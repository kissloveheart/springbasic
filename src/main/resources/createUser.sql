INSERT INTO "PUBLIC"."ROLE" VALUES
(2, 'ADMIN'),
(3, 'USER');               

INSERT INTO "PUBLIC"."USER_APP" VALUES
(1, DATE '2022-01-20', 'admin@admin.com', TRUE, '$2a$10$f7QCa1euQIci96KeGFZ.meDqNC53EFoeK0ic0T94AE/SJIhB2kKxy'),
(4, DATE '2022-01-20', 'user@user.com', TRUE, '$2a$10$f7QCa1euQIci96KeGFZ.meDqNC53EFoeK0ic0T94AE/SJIhB2kKxy');      
               
INSERT INTO "PUBLIC"."USER_ROLE" VALUES
(1, 2),
(1, 3),
(4, 3);            
            
             
