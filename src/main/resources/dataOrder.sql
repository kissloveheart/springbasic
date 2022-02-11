
INSERT INTO "PUBLIC"."CATEGORY" VALUES
(1, 'Phone'),
(2, 'Laptop');         

 
INSERT INTO "PUBLIC"."PRODUCT" VALUES
(1, NULL, 'Iphone 8', 399.0, 1),
(2, NULL, 'Iphone 9', 499.0, 1),
(3, NULL, 'Iphone 10', 599.0, 1),
(4, NULL, 'Iphone 11', 699.0, 1),
(5, NULL, 'Dell X99', 1999.0, 2),
(6, NULL, 'Lenovo Y11', 999.0, 2);        
   
INSERT INTO "PUBLIC"."ROLE" VALUES
(1, 'USER'),
(2, 'ADMIN');               

INSERT INTO "PUBLIC"."USER_APP" VALUES
(1, NULL, 3004.0, DATE '2022-02-11', 'admin@admin.com', TRUE, '$2a$10$f7QCa1euQIci96KeGFZ.meDqNC53EFoeK0ic0T94AE/SJIhB2kKxy', NULL, NULL),
(2, NULL, 6.0, DATE '2022-02-11', 'user@user.com', TRUE, '$2a$10$f7QCa1euQIci96KeGFZ.meDqNC53EFoeK0ic0T94AE/SJIhB2kKxy', NULL, NULL),
(3, NULL, 196204.0, DATE '2022-02-11', 'heroy67378@alfaceti.com', TRUE, '$2a$10$3tjkZKxb8f0aKni/Dk6J4.70xHYDQ6iLpBkrkwfEtrd1weIZuQfkW', NULL, NULL);

INSERT INTO "PUBLIC"."USER_ROLE" VALUES
                                     (1, 1),
                                     (1, 2),
                                     (2, 1),
                                     (3, 1);

INSERT INTO "PUBLIC"."VERIFICATION_TOKEN" VALUES
(1, TIMESTAMP '2022-02-12 09:32:26.947', '73f5f0ec-21aa-4c76-88e8-60e506610d34', 3);

INSERT INTO "PUBLIC"."ORDERS" VALUES
                                  (1, TIMESTAMP '2022-02-11 09:33:00.412', 1098.0, 3),
                                  (2, TIMESTAMP '2022-02-11 09:33:08.723', 2698.0, 3),
                                  (3, TIMESTAMP '2022-02-11 09:33:26.023', 2997.0, 2),
                                  (4, TIMESTAMP '2022-02-11 09:33:39.239', 1498.0, 2),
                                  (5, TIMESTAMP '2022-02-11 09:33:44.777', 499.0, 2),
                                  (6, TIMESTAMP '2022-02-11 09:34:02.9', 898.0, 1),
                                  (7, TIMESTAMP '2022-02-11 09:34:08.794', 1098.0, 1);

INSERT INTO "PUBLIC"."ORDER_DETAIL" VALUES
                                        (1, 599.0, 1, 1, 3),
                                        (2, 499.0, 1, 1, 2),
                                        (3, 699.0, 1, 2, 4),
                                        (4, 1999.0, 1, 2, 5),
                                        (5, 599.0, 1, 3, 3),
                                        (6, 1999.0, 1, 3, 5),
                                        (7, 399.0, 1, 3, 1),
                                        (8, 999.0, 1, 4, 6),
                                        (9, 499.0, 1, 4, 2),
                                        (10, 499.0, 1, 5, 2),
                                        (11, 399.0, 1, 6, 1),
                                        (12, 499.0, 1, 6, 2),
                                        (13, 699.0, 1, 7, 4),
                                        (14, 399.0, 1, 7, 1);


