create database qr_payment_verifier;
use qr_payment_verifier;
create table `transaction` (
    id VARCHAR(20) primary key,
    bank_brand_name VARCHAR(20),
    account_number VARCHAR(20),
    transaction_date DATETIME,
    amount_out DECIMAL(15,2),
    amount_in DECIMAL(15,2),
    accumulated DECIMAL(15,2),
    transaction_content TEXT,
    reference_number VARCHAR(30),
    code VARCHAR(20),
    sub_account VARCHAR(50),
    bank_account_id VARCHAR(20),
    is_read BOOLEAN
);