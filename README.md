# KeyGenTest
requenst to 
http://127.0.0.1:8080/AuthInterface/v2/coorauth/1
result is:
```json
[
   {
      "id": 2,
      "coordinatorId": 1,
      "authCode": [
         0,
         18,
         75,
         0,
         4,
         15,
         26,
         60,
         0,
         0,
         0,
         0,
         0,
         0,
         0,
         0
      ],
      "encryptionKey": [
         47,
         -50,
         -42,
         125,
         -46,
         103,
         42,
         -107,
         38,
         25,
         31,
         -122,
         0,
         51,
         -45,
         -7
      ],
      "smartmeterAuthApis": [
         {
            "id": 1,
            "smId": 3,
            "coordinatorId": 1,
            "authCode": [
               0,
               18,
               75,
               0,
               4,
               15,
               26,
               60,
               0,
               0,
               0,
               0,
               0,
               0,
               0,
               0
            ],
            "encryptionKey": [
               -70,
               -117,
               7,
               54,
               -100,
               -54,
               -123,
               58,
               87,
               -49,
               -57,
               119,
               -17,
               -99,
               4,
               -32
            ]
         },
         {
            "id": 2,
            "smId": 4,
            "coordinatorId": 1,
            "authCode": [
               0,
               18,
               75,
               0,
               4,
               15,
               26,
               60,
               0,
               0,
               0,
               0,
               0,
               0,
               0,
               0
            ],
            "encryptionKey": [
               100,
               -8,
               48,
               -65,
               0,
               -81,
               -103,
               92,
               55,
               83,
               104,
               -80,
               20,
               -70,
               28,
               -29
            ]
         }
      ]
   }
]
```


request is :http://127.0.0.1:8080/AuthInterface/v2/smartmeterauth
```json
[
   {
      "id": 1,
      "smId": 3,
      "coordinatorId": 1,
      "authCode": [
         0,
         18,
         75,
         0,
         4,
         15,
         26,
         60,
         0,
         0,
         0,
         0,
         0,
         0,
         0,
         0
      ],
      "encryptionKey": [
         -70,
         -117,
         7,
         54,
         -100,
         -54,
         -123,
         58,
         87,
         -49,
         -57,
         119,
         -17,
         -99,
         4,
         -32
      ]
   },
   {
      "id": 2,
      "smId": 4,
      "coordinatorId": 1,
      "authCode": [
         0,
         18,
         75,
         0,
         4,
         15,
         26,
         60,
         0,
         0,
         0,
         0,
         0,
         0,
         0,
         0
      ],
      "encryptionKey": [
         100,
         -8,
         48,
         -65,
         0,
         -81,
         -103,
         92,
         55,
         83,
         104,
         -80,
         20,
         -70,
         28,
         -29
      ]
   }
]
```


