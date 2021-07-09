FROM myneva-cloud-base-image

ENV SRC_DIR /sso-backend

ENV PORT 8085
ENV KEYCLOAK_REALM login-test-realm
ENV KEYCLOAK_ADDRESS="http://localhost:8080"
ENV KEYCLOAK_CLIENT login-test-client
ENV KEYCLOAK_CREDENTIALS_SECRET=""

WORKDIR $SRC_DIR
COPY . .

EXPOSE $PORT

RUN chmod +x docker-entrypoint.sh
ENTRYPOINT ["./docker-entrypoint.sh"]


