# ERPLITE Prototyp

## Einführung

Der Prototyp zeig einen **Modulithen** mit einigen Teilfunktionalitäten zum Thema Bestellungen - Lager - Kunden.

Folgende Teilmodule werden als Protoypen angeboten:

1) Customermanagement (nur rudimentär vorhanden)
2) Stockmanagement (nur rudimentär vorhanden)
3) Ordermanagement (Ports and Adapters, taktische Muster aus DDD)

Die Applikation ist im Modul **Ordermanagement** nach der **Ports-And-Adapters-Architektur** aufgebaut.

Im Ordermanagement wurden außerdem einige taktische Muster aus dem **Domain-driven Design (DDD)** eingesetzt, darunter:

* ValueObjects
* Aggregates
* Repositories (auf Aggregat-Ebene)
* Event-getriebene Kommunikation zwischen Aggregaten

Das ist auch der eigentliche Zweck dieses Prototypen: zu zeigen, wie Ports-And-Adapters und Teile aus DDD umgesetzt
werden können.

Das **Stockmanagement** folgt nur einer leichtgewichtigen Schichtung ohne strenge Trennung zwischen Technologie und
Domäne.

Das **Customermanagement** steht nur rudimentär zur Verfügung, setzt aber im Anfangsstadium P&A sowie einige DDD-Muster
um.

Für das **Stockmanagement** und das Customermanagement werden mit Spring Data Rest automationsunterstützt REST-APIs zu
den vorhandenen ausimplementierten Methoden bereitgestellt.

Das **Ordermanagement** hat keine Spring Data Rest Unterstützung konfiguriert.

## Inbetriebnahme

Die App kann als Maven-Projekt importiert und gestartet werden.

Es bestehen keine Abhängigkeiten zu weiteren Infrastrukturkomponenten.

* Datenbank:
    * H2 (in memory)
    * Die Konsole ist erreichbar über: http://localhost:8080/h2-console/
* Messaging: Spring Application Events
    * asynchron für die Modul-zu-Modul-Kommunikation zwischen Ordermanagement und Stockmanagement
    * synchron für die Domain-Events
* API-Dokumentation:
    * Die API kann über Swagger erkundet werden
    * Die Swagger und OpenAPI-Docu ist erreichbar unter: http://localhost:8080/swagger/swagger-ui.html

Im Package _/test/java/at.kolleg.erplite_ steht ein automatisierter EndToEnd Test zur Verfügung, der einen
Anwendungsfall der Applikation komplett durchtestet (REST-Requests mit RestTemplate):

``` JAVA
public void placeOrderMakePaymentCheckGetOrderCheckOrderStatusCheckStock()
```

Der im automatisierten Test ausgeführte Beispielablauf wird im Folgenden dokumentiert und kann natürlich auch "händisch"
über die Anwendung der REST-Endpunkte (z.B. über PostMan) durchgespielt werden.

### Beispielablauf

#### Sequenzdiagramm

Das folgende Sequenzdiagramm zeigt einen Beispielablauf für einige zentrale im Prototyp ausimplementierten Use-Cases.

``` mermaid
sequenceDiagram
actor LagerApiUser
actor Ordermanager
actor ShopApiUser
ShopApiUser->>Ordermanagement: [Place Order] POST auf /api/v1/orders/
Ordermanagement-->>ShopApiUser: New Order with generated ID, z. B. ONR8e04b1f
Ordermanager->>+Ordermanagement: [VERIFY PAYMENT] POST auf /api/v1/orders/checkpayment/{orderid}
Ordermanagement->>Ordermanagement: Zustandswechel nach PAYMENT_VERIFIED
Ordermanagement-)Stockmanagement: PAYMENT_VERIFIED Nachricht (async)
Stockmanagement->>Stockmanagement: Packliste anlegen
LagerApiUser->>Stockmanagement: Listen-Item als verpackt markieren: POST auf /stock/setPackedForPacking/{id}
 alt Alle Items einer Bestellung verpackt
       Stockmanagement-)Ordermanagement: Nachricht (async), dass alles verpackt ist.
 end
Ordermanagement ->>+Ordermanagement: Zustandswechsel nach PREPARING_FOR_DELIVERY
```

Es folgt die Beschreibung des Ablaufs mit den konkreten API-Calls.

#### Bestellung platzieren

Zunächst wird eine Bestellung platziert:

``` JSON
POST http://localhost:8080/api/v1/orders/
Content-Type: application/json

{
"customerID": "CUS1d34e56",
"customerFirstname": "Caesar",
"customerLastname": "Franklin",
"customerEmail": "a.b@c.de",
"customerStreet": "Hollywood Boulevard 2",
"customerZipcode": "3452",
"customerCity": "LA",
"customerCountry": "USA",
"cartItems": [
{
"productNumber": "P123RE123D",
"productName": "MacBook Pro 2022",
"priceNet" : 1000,
"tax": 20,
"amount": 1
},
{
"productNumber": "O12345RE12",
"productName": "Ipad Pro 2021",
"priceNet": 99.99,
"tax": 10,
"amount": 10
}
]
}

```

Es wird eine Bestellung angelegt, die eine neue ID bekommen hat:

``` JSON
HTTP/1.1 201
Location: http: //localhost:8080/orders/ONR8e04b1f
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 04 May 2022 12: 43: 53 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
    "orderID": "ONR8e04b1f",
    "customerID": "CUS1d34e56",
    "customerFirstname": "Caesar",
    "customerLastname": "Franklin",
    "customerEmail": "a.b@c.de",
    "customerStreet": "Hollywood Boulevard 2",
    "customerZipcode": "3452",
    "customerCity": "LA",
    "customerCountry": "USA",
    "orderLineItems": [
        {
        "productNumber": "P123RE123D",
        "productName": "MacBook Pro 2022",
        "priceNet": 1000.0,
        "tax": 20,
        "amount": 1
        },
        {
        "productNumber": "O12345RE12",
        "productName": "Ipad Pro 2021",
        "priceNet": 99.99,
        "tax": 10,
        "amount": 10
        }
    ],
    "state": "PLACED",
    "taxTotal": 299.99,
    "netTotal": 1999.9,
    "grossTotal": 2299.89,
    "date": "2022-05-04T14:43:53.756530"
}
```

#### Bestätigung der Zahlung (händisch)

Wenn die Bestellung per (neuer) ID durchgeführt wurde, kann händisch der Zahlungseingang bestätigt werden:

``` JSON
POST http://localhost:8080/api/v1/orders/checkpayment/ONR8e04b1f
```

Daraufhin wechselt die Bestellung in den Zustand PAYMENT_VERIFIED. Die Bestelldaten können wie folgt abgerufen werden:

``` JSON
GET http://localhost:8080/api/v1/orders/ONR8e04b1f
```

#### Packlisten

Das Ordermanagement sendet nach Bestätigung der Zahlung ein entsprechendes Event aus, das vom Stockmanagement empfangen
wird. Im Stockmanagement wird ein neuer Eintrag für eine Packliste erstellt.

Die Packlisten können so abgefragt werden:

``` JSON
GET http://localhost:8080/stock/packings
```

Das Ergebnis sind die Packlisten aus dem Stockmanagement zusammen mit ihren Listeneinträgen (zu verpackende Items):

``` JSON
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 04 May 2022 12: 51: 48 GMT
Keep-Alive: timeout=60
Connection: keep-alive

[
    {
        "id": 4,
        "orderId": "ONR8998527",
        "deliveryData": {
        "name": "Caesar Franklin",
        "street": "Hollywood Boulevard 2",
        "zipcode": "3452",
        "city": "LA",
        "country": "USA"
        },
        "packingItemList": [
            {
            "id": 5,
            "productNumber": "P123RE123D",
            "productName": "MacBook Pro 2022",
            "amount": 1,
            "packed": false
            },
            {
            "id": 6,
            "productNumber": "O12345RE12",
            "productName": "Ipad Pro 2021",
            "amount": 10,
            "packed": false
            }
        ]
    }
]
```

Die Packings für eine bestimmte Bestellung werden so abgefragt:

``` JSON
GET http://localhost:8080/stock/packings/whithorderid/ONR8998527
```

Die Packings können auch nach Packing-ID abgefragt werden:

``` JSON
GET http://localhost:8080/stock/packings/4
```

#### Packlisten-Items als verpackt markieren

Die einzelnen Items der Packlisten können per ID als verpackt markiert werden:

``` JSON
POST http://localhost:8080/stock/setPackedForPacking/5
POST http://localhost:8080/stock/setPackedForPacking/6
```

#### Bestellung wird versendet

Wenn im Stockmanagement alle Items einer Packliste verpackt sind, wird ein Event ausgelöst, das das Ordermanagement
empfängt und daraufhin den Zustand der Bestellung in PREPARING_FOR_DELIVERY ändert.

Die Bestellung kann wieder abgefragt werden:

``` JSON
GET http://localhost:8080/api/v1/orders/ONR8998527
```

Das Ergebnis ist die Bestellung, die nun den neuen Zustand aufweist.

``` JSON
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 04 May 2022 13:27:58 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
  "orderID": "ONR8998527",
  "customerID": "CUS1d34e56",
  "customerFirstname": "Caesar",
  "customerLastname": "Franklin",
  "customerEmail": "a.b@c.de",
  "customerStreet": "Hollywood Boulevard 2",
  "customerZipcode": "3452",
  "customerCity": "LA",
  "customerCountry": "USA",
  "orderLineItems": [
    {
    "productNumber": "P123RE123D",
    "productName": "MacBook Pro 2022",
    "priceNet": 1000.0,
    "tax": 20,
    "amount": 1
    },
    {
    "productNumber": "O12345RE12",
    "productName": "Ipad Pro 2021",
    "priceNet": 99.99,
    "tax": 10,
    "amount": 10
    }
  ],
  "state": "PREPARING_FOR_DELIVERY",
  "taxTotal": 299.99,
  "netTotal": 1999.9,
  "grossTotal": 2299.89,
  "date": "2022-05-04T14:51:31.068842"
}
```
