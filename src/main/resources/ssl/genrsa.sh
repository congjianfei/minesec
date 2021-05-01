# CA
openssl genrsa -out ca.key
openssl req -new -key ca.key -out ca.csr
openssl x509 -req -in ca.csr -signkey ca.key -out ca.crt

# Server
openssl genrsa -out server.key
openssl req -new -key server.key -out server.csr
openssl x509 -req -CA ca.crt -CAkey ca.key -CAcreateserial -in server.csr -out server.crt
# Client
openssl genrsa -out client.key
openssl req -new -key client.key -out client.csr
openssl x509 -req -CA ca.crt -CAkey ca.key -CAcreateserial -in client.csr -out client.crt