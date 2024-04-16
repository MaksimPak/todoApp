#!/bin/bash

awslocal s3api \
create-bucket --bucket todoapp-s3 \
--create-bucket-configuration LocationConstraint=eu-central-1 \
--region eu-central-1

awslocal s3api put-bucket-cors --bucket todoapp-s3 --cors-configuration '{
  "CORSRules":[
    {
      "AllowedHeaders":[
        "*"
      ],
      "AllowedMethods":[
        "GET",
        "POST",
        "PUT"
      ],
      "AllowedOrigins":[
        "*"
      ],
      "ExposeHeaders":[]
    }
  ]
}'