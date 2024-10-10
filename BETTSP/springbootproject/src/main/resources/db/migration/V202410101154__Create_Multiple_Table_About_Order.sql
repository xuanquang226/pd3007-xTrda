CREATE TABLE IF NOT EXISTS public.account(id SERIAL PRIMARY KEY, user_name VARCHAR(255), password VARCHAR(255)
                                        ,account_type VARCHAR(32), id_customer BIGINT);
CREATE TABLE IF NOT EXISTS public.customer(id SERIAL PRIMARY KEY, name VARCHAR(255), mail VARCHAR(255)
                                        ,location VARCHAR(255), id_account BIGINT);   
CREATE TABLE IF NOT EXISTS public.cart(id SERIAL PRIMARY KEY, id_customer BIGINT, total_price VARCHAR(32)
                                        ,status VARCHAR(32), code_discount VARCHAR(32), notes VARCHAR(255));       
CREATE TABLE IF NOT EXISTS public.cart_item(id SERIAL PRIMARY KEY, id_cart BIGINT, id_product BIGINT, quantity BIGINT
                                            , price VARCHAR(32), note VARCHAR(255), name VARCHAR(255));
CREATE TABLE IF NOT EXISTS public.order(id SERIAL PRIMARY KEY, id_customer BIGINT, total_price VARCHAR(32), status VARCHAR(32)
                                        ,code_discount VARCHAR(32), notes VARCHAR(255));  
CREATE TABLE IF NOT EXISTS public.order_line(id SERIAL PRIMARY KEY, id_order BIGINT, id_product BIGINT, name VARCHAR(255)
                                            ,quantity BIGINT, price VARCHAR(32), note VARCHAR(255));  

ALTER TABLE public.product ADD COLUMN IF NOT EXISTS price VARCHAR(32);
ALTER TABLE public.product ADD COLUMN IF NOT EXISTS quantity BIGINT;                                                                                                                                                  