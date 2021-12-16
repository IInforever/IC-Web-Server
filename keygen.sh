openssl ecparam -name prime256v1 -genkey -noout -out ec-key.pem
openssl pkcs8 -topk8 -inform pem -in ec-key.pem -outform pem -nocrypt -out ecc-private-key.pem
openssl ec -in ecc-private-key.pem -pubout -out ecc-public-key.pem