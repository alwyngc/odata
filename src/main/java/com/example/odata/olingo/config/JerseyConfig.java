package com.example.odata.olingo.config;



import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.core.rest.ODataRootLocator;
import org.apache.olingo.odata2.core.rest.app.ODataApplication;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

@Component
@ApplicationPath("/odata")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig(LibraryAppOdataServiceFactory serviceFactory, EntityManagerFactory entityManagerFactory) {
        ODataApplication oDataApplication = new ODataApplication();
        oDataApplication
                .getClasses()
                .forEach( c -> {
                    if ( !ODataRootLocator.class.isAssignableFrom(c)) {
                        register(c);
                    }
                });
        register(new ODataServiceRootLocator(serviceFactory));
        register(new EntityManagerFilter(entityManagerFactory));
    }

    @Path("/")
    public static class ODataServiceRootLocator extends ODataRootLocator {

        private LibraryAppOdataServiceFactory serviceFactory;

        @Inject
        public ODataServiceRootLocator (LibraryAppOdataServiceFactory serviceFactory) {
            this.serviceFactory = serviceFactory;
        }

        @Override
        public ODataServiceFactory getServiceFactory() {
            return this.serviceFactory;
        }
    }

    @Provider
    public static class EntityManagerFilter implements ContainerRequestFilter,
            ContainerResponseFilter {
        public static final String EM_REQUEST_ATTRIBUTE =
                EntityManagerFilter.class.getName() + "_ENTITY_MANAGER";
        private final EntityManagerFactory entityManagerFactory;

        @Context
        private HttpServletRequest httpRequest;
        public EntityManagerFilter(EntityManagerFactory entityManagerFactory) {
            this.entityManagerFactory = entityManagerFactory;
        }

        @Override
        public void filter(ContainerRequestContext containerRequestContext) throws java.io.IOException {
            EntityManager entityManager = (EntityManager) this.entityManagerFactory.createEntityManager();
            httpRequest.setAttribute(EM_REQUEST_ATTRIBUTE, entityManager);
            if (!"GET".equalsIgnoreCase(containerRequestContext.getMethod())) {
                entityManager.getTransaction().begin();
            }
        }
        @Override
        public void filter(ContainerRequestContext requestContext,
                           ContainerResponseContext responseContext) throws java.io.IOException {
            EntityManager entityManager = (EntityManager) httpRequest.getAttribute(EM_REQUEST_ATTRIBUTE);
            if (!"GET".equalsIgnoreCase(requestContext.getMethod())) {
                EntityTransaction entityTransaction = entityManager.getTransaction(); //we do not commit because it's just a READ
                if (entityTransaction.isActive() && !entityTransaction.getRollbackOnly()) {
                    entityTransaction.commit();
                }
            }
            entityManager.close();
        }
    }

}