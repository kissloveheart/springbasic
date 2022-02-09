INSERT INTO "PUBLIC"."ROLE" VALUES
                                (1, 'ADMIN'),
                                (2, 'USER');

INSERT INTO "PUBLIC"."USER_APP" VALUES
                                    (1, NULL, 5000.0, DATE '2022-02-09', 'admin@admin.com', TRUE, '$2a$10$f7QCa1euQIci96KeGFZ.meDqNC53EFoeK0ic0T94AE/SJIhB2kKxy', NULL, NULL),
                                    (2, NULL, 5000.0, DATE '2022-02-09', 'user@user.com', TRUE, '$2a$10$f7QCa1euQIci96KeGFZ.meDqNC53EFoeK0ic0T94AE/SJIhB2kKxy', NULL, NULL);

INSERT INTO "PUBLIC"."USER_ROLE" VALUES
                                     (1, 1),
                                     (1, 2),
                                     (2, 2);
            
             
