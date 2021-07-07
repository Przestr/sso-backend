eg.

docker build -t myneva-cloud-sso-server-testing-app .

docker run --name myneva-cloud-sso-server-testing-app 
    -p 8085:8085 
    -e KEYCLOAK_REALM="login-test-realm"
    -e KEYCLOAK_ADDRESS="http://localhost:8080"
    -e KEYCLOAK_CLIENT="login-test-client"
    -e KEYCLOAK_CREDENTIALS_SECRET="f8ca441c-a25c-4f54-ae99-5c851d40391b"
    myneva-cloud-sso-server-testing-app

KEYCLOAK_CREDENTIALS_SECRET is required.
