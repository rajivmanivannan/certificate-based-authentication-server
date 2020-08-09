#To install JAVA
brew cask install java

# To Install new OpenSSL
brew install openssl

# ------------------------------------------ Self Signed Root CA -----------------------------------------------
# To create Self Signed Root CA
openssl req -x509 -sha256 -days 3650 -newkey rsa:4096 -keyout rootCA.key -out rootCA.crt

# ------------------------------------------ Server Side Certificate -------------------------------------------

# To create Server Side Certificate
## Step #1 Certificate Signing Request (CSR)
openssl req -new -newkey rsa:4096 -keyout localhost.key -out localhost.csr
## Step #2 Sign the request with our rootCA.crt certificate and its private key
openssl x509 -req -CA rootCA.crt -CAkey rootCA.key -in localhost.csr -out localhost.crt -days 365 -CAcreateserial -extfile localhost.ext
## To print our certificate details in human readable format
openssl x509 -in localhost.crt -text

# ------------------------------------------ Import to the Keystore --------------------------------------------

# To import the signed certificate and the corresponding private key to the keystore.jks file. (Keystore file)
## Step #1 Pack our server's private key together with the signed certificate into the PKCS file (.p12 file extension)
openssl pkcs12 -export -out localhost.p12 -name "localhost" -inkey localhost.key -in localhost.crt
## To create a keystore.jks repository and import the localhost.p12 file (Using Java Keytool)
keytool -importkeystore -srckeystore localhost.p12 -srcstoretype PKCS12 -destkeystore keystore.jks -deststoretype JKS

# ------------------------------------------ Import to the Truststore --------------------------------------------

# To create Truststore to hold our CA root certificate. (Truststore holds the certificates of the external entities that we trust)
keytool -import -trustcacerts -noprompt -alias ca -ext san=dns:localhost,ip:127.0.0.1 -file rootCA.crt -keystore truststore.jks

# ------------------------------------------ Client Side Certificate -------------------------------------------

#To create Client-side Certificate
## Step #1 Certificate Signing Request (CSR)
openssl req -new -newkey rsa:4096 -nodes -keyout clientAlice.key -out clientAlice.csr
## Step #2 Sign the request with our rootCA.crt certificate and its private key
openssl x509 -req -CA rootCA.crt -CAkey rootCA.key -in clientAlice.csr -out clientAlice.crt -days 365 -CAcreateserial
## Step #3 Package the signed certificate and the private key into the PKCS file (.p12 file extension)
openssl pkcs12 -export -out clientAlice.p12 -name "clientAlice" -inkey clientAlice.key -in clientAlice.crt

