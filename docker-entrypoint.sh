#!/bin/bash
set -eou pipefail

SYS_PROPS=""

if [[ -n ${KEYCLOAK_REALM:-} ]]; then
    SYS_PROPS+="--keycloak.realm=$KEYCLOAK_REALM "
    echo $SYS_PROPS
fi

if [[ -n ${KEYCLOAK_ADDRESS:-} ]]; then
    SYS_PROPS+="--keycloak.auth-server-url=$KEYCLOAK_ADDRESS/auth "
    echo $SYS_PROPS
fi

if [[ -n ${KEYCLOAK_CLIENT:-} ]]; then
    SYS_PROPS+="--keycloak.resource=$KEYCLOAK_CLIENT "
    echo $SYS_PROPS
fi

if [[ -n ${KEYCLOAK_CREDENTIALS_SECRET:-} ]]; then
    SYS_PROPS+="--keycloak.credentials.secret=$KEYCLOAK_CREDENTIALS_SECRET "
    echo $SYS_PROPS
else
    echo "Credentials secret is empty"
    exit
fi

PROPS_TMP=$SYS_PROPS
SYS_PROPS=${PROPS_TMP%?}

echo "PROPS $SYS_PROPS"
exec ./mvnw spring-boot:run -Dspring-boot.run.arguments="$SYS_PROPS"