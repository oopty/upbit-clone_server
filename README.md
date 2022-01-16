# Downbit 서버 프로젝트

## Step1
### 요구사항
- 계좌
  - 사용자는 어떤 통화의 잔고와 사용 가능한/사용 불가능한 잔고를 조회할 수 있다.
- 조회
  - 사용자는 모든 통화 코드 리스트를 조회할 수 있다.
  - 사용자는 모든 통화의 가격을 조회 가능하다.
    - 사용자는 어떤 통화의 tick들을 조회 가능하다.
    - 사용자는 분 단위로 어떤 통화의 캔들 크기를 지정해 캔들 정보를 조회할 수 있다.
  - 사용자는 어떤 통화의 호가창을 조회할 수 있다.
- 주문
  - 사용자는 매수/매도가 가능하다.
    - 사용자는 어떤 통화를 시장가 매수/매도가 가능하다.
    - 사용자는 어떤 통화를 지정가 매수/매도가 가능하다.
- 입금
  - 사용자는 원화를 입금할 수 있다.
- 출금
  - 사용자는 원화를 출금할 수 있다.
### API 구현 사항
1. 사용자는 어떤 통화의 잔고와 사용 가능한/사용 불가능한 잔고를 조회할 수 있다.

 - 마켓 코드 정보를 주면 통화에 대한 내 소유 정보를 보여준다.
 - 소유정보는 총 자산과 이용가능 자산과 이용 불가능 자산으로 이루어져있다.
 - 이용 가능 자산은 출금/매매 등 다양한 서비스를 이용할 때 사용할 수 있는 자산을 의미한다.
 - 이용 불가능 자산은 현재 특정 서비스를 이용하는데 사용중이어서 다른 서비스로 사용하지 못하는 자산을 의미한다.

**/api/v1/markets/:marketCode**

Request Format  

| 이름         | 타입     | 필수  | 검증       | 설명    |
|------------|--------|-----|----------|-------|
| marketCode | String | O   | 유효한 마켓코드 | 마켓코드명 |

Response Format

| 이름               | 타입     | 설명        |
|------------------|--------|-----------|
| marketCode       | String | 마켓코드      |
| balance          | double | 총 자산      |
| availableBalance | double | 이용 가능 자산  |
| lockedBalance    | double | 이용 불가능 자산 |

2. 사용자는 어떤 통화의 tick들을 조회 가능하다.
 - tick이란 현재 해당 통화의 스냅샷을 의미한다.
 - tick을 구성하는 내용은 캔들 기준 시각, 시가, 고가, 저가, 종가, 부호 있는 변화액과 변화율이다

**/api/v1/ticks/:marketCode**

Request Format  

| 이름         | 타입     | 필수  | 검증       | 설명    |
|------------|--------|-----|----------|-------|
| marketCode | String | O   | 유효한 마켓코드 | 마켓코드명 |

Response Format

| 이름                | 타입     | 설명         |
|-------------------|--------|------------|
| marketCode        | String | 마켓코드       |
| candleDateTime    | String | 캔들 기준 시각   |
| openingPrice      | double | 시가         |
| highPrice         | double | 고가         |
| lowPrice          | double | 저가         |
| tradePrice        | double | 종가         |
| signedChangePrice | double | 부호가 있는 변화액 |
| signedChangeRate  | double | 부호가 있는 변화율 |

3. 사용자는 분 단위로 어떤 통화의 캔들 크기를 지정해 캔들 정보를 조회할 수 있다.
 - 요청 정보로 marketCode랑 캔들 크기를 지정한다.
 - 캔들 크기로는 1, 3, 5, 10, 15, 60, 240을 지정할 수 있다.

**/api/v1/candles/:marketCode**

Request Format

| 이름         | 타입     | 필수  | 검증                       | 설명    |
|------------|--------|-----|--------------------------|-------|
| marketCode | String | O   | 유효한 마켓코드                 | 마켓코드명 |
| unit       | int    | O   | 1, 3, 5, 10, 15, 60, 240 | 마켓코드명 |

Response Format

| 이름             | 타입     | 설명       |
|----------------|--------|----------|
| marketCode     | String | 마켓코드     |
| candleDateTime | String | 캔들 기준 시각 |
| openingPrice   | double | 시가       |
| highPrice      | double | 고가       |
| lowPrice       | double | 저가       |
| tradePrice     | double | 종가       |

4. 사용자는 어떤 통화를 시장가 매수/매도가 가능하다.
5. 사용자는 어떤 통화를 지정가 매수/매도가 가능하다.
 - Request로 받는 인자는 마켓코드, 주문 종류, 주문 종류, 주문양과, 주문 가격이다.
 - 
**/api/v1/orders**

Request Format

| 이름         | 타입     | 필수     | 검증                                             | 설명    |
|------------|--------|--------|------------------------------------------------|-------|
| marketCode | String | O      | 유효한 마켓코드                                       | 마켓코드명 |
| side       | String | O      | 'bid' or 'ask'                                 | 마켓코드명 |
| orderType  | String | O      | limit, <br/>price(시장가 매수), <br/>market(시장가 매도) | 마켓코드명 |
| volume     | double | 매도시 필수 | 주문양 <= 소유양                                     | 마켓코드명 |
| price      | double | 매수시 필수 |                                                | 마켓코드명 |

Response Format

| 이름              | 타입     | 설명         |
|-----------------|--------|------------|
| uuid            | String | 주문의 고유 아이디 |
| side            | String | 주문 종류      |
| orderType       | String | 주문 방식      |
| avg_price       | double | 체결 평균가     |
| state           | String | 주문 상태      |
| createdAt       | String | 주문 생성 시간   |
| volume          | double | 주문양        |
| executedVolume  | double | 체결된 주문양    |
| remainingVolume | double | 남은 주문양     |
| tradeCount      | int    | 체결 수       |

6. 사용자는 원화를 입금할 수 있다.

**/api/v1/deposits**

Request Format

| 이름     | 타입  | 필수  | 검증  | 설명  |
|--------|-----|-----|-----|-----|
| amount | int | O   | X   | 주문양 |

Response Format

| 이름               | 타입     | 설명         |
|------------------|--------|------------|
| uuid             | String | 주문의 고유 아이디 |
| createdAt        | String | 입금 생성시간    |
| amount           | int    | 입금액        |


7. 사용자는 원화를 출금할 수 있다.

**/api/v1/deposits**

Request Format

| 이름     | 타입  | 필수  | 검증  | 설명  |
|--------|-----|-----|-----|-----|
| amount | int | O   | X   | 주문양 |

Response Format

| 이름        | 타입     | 설명         |
|-----------|--------|------------|
| uuid      | String | 주문의 고유 아이디 |
| createdAt | String | 출금 생성시간    |
| amount    | int    | 출금액        |