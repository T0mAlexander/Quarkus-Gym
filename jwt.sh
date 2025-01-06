#!/bin/bash

# Nova chave privada
openssl genpkey -algorithm RSA -out src/main/resources/keys/jwt-privkey.pem -pkeyopt rsa_keygen_bits:4096

# Extração da chave pública
openssl rsa -pubout -in src/main/resources/keys/jwt-privkey.pem -out src/main/resources/keys/jwt.pem