create table account_details_paytm
(
  mobileNo number(10) primary key,
  name varchar2(20),
  walletid number(6) references wallet_account_details(walletid)
);

create table wallet_account_details
(
  walletId number(6) primary key,
  balance number(10,2) 
);

create table paytm_transaction_histories
(
 transactionId number(6) primary key,
 mobileNo varchar2(10),
 transaction_date Date,
 type varchar2(10),
 amount number(10,2)
);