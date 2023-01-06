INSERT INTO workers(first_name) VALUES('1');

INSERT INTO favors(worker_id, favor_name, price, status) VALUES(1, '1', 100, 'NOT_PAID');
INSERT INTO favors(worker_id, favor_name, price, status) VALUES(1, '2', 100, 'PAID');

INSERT INTO orders(problem_description, status) VALUES('1', 'COMPLETED');
INSERT INTO orders_favors(order_id, favors_id) VALUES(1, 1);
INSERT INTO orders_favors(order_id, favors_id) VALUES(1, 2);
