INSERT INTO product(name,code) VALUES('product_0', 100);
INSERT INTO product(name,code) VALUES('product_0', 101);
INSERT INTO product(name,code) VALUES('product_0', 102);
INSERT INTO product(name,code) VALUES('product_0', 103);
INSERT INTO product(name,code) VALUES('product_0', 104);
INSERT INTO product(name,code) VALUES('product_0', 105);
INSERT INTO product(name,code) VALUES('product_0', 106);
INSERT INTO product(name,code) VALUES('product_0', 107);
INSERT INTO product(name,code) VALUES('product_0', 108);
INSERT INTO product(name,code) VALUES('product_0', 109);

INSERT INTO organization(name,INN,account) VALUES('organization_0',1000,2000);
INSERT INTO organization(name,INN,account) VALUES('organization_1',1001,2001);
INSERT INTO organization(name,INN,account) VALUES('organization_2',1002,2002);
INSERT INTO organization(name,INN,account) VALUES('organization_3',1003,2003);
INSERT INTO organization(name,INN,account) VALUES('organization_4',1004,2004);
INSERT INTO organization(name,INN,account) VALUES('organization_5',1005,2005);
INSERT INTO organization(name,INN,account) VALUES('organization_6',1006,2006);
INSERT INTO organization(name,INN,account) VALUES('organization_7',1007,2007);
INSERT INTO organization(name,INN,account) VALUES('organization_8',1008,2008);
INSERT INTO organization(name,INN,account) VALUES('organization_9',1009,2009);

INSERT INTO item(price,product_id,quantity) VALUES (89,1,50);
INSERT INTO item(price,product_id,quantity) VALUES (89,2,51);
INSERT INTO item(price,product_id,quantity) VALUES (90,3,51);
INSERT INTO item(price,product_id,quantity) VALUES (91,4,52);
INSERT INTO item(price,product_id,quantity) VALUES (92,5,53);
INSERT INTO item(price,product_id,quantity) VALUES (93,6,54);
INSERT INTO item(price,product_id,quantity) VALUES (94,7,55);
INSERT INTO item(price,product_id,quantity) VALUES (95,8,56);
INSERT INTO item(price,product_id,quantity) VALUES (96,9,57);
INSERT INTO item(price,product_id,quantity) VALUES (96,9,57);

INSERT INTO waybill(number,"date",organization_INN) VALUES (101,'2021-11-02',1000);
INSERT INTO waybill(number,"date",organization_INN) VALUES (102,'2021-11-03',1000);
INSERT INTO waybill(number,"date",organization_INN) VALUES (103,'2021-11-03',1001);
INSERT INTO waybill(number,"date",organization_INN) VALUES (104,'2021-11-04',1002);
INSERT INTO waybill(number,"date",organization_INN) VALUES (105,'2021-11-05',1003);
INSERT INTO waybill(number,"date",organization_INN) VALUES (106,'2021-11-06',1004);
INSERT INTO waybill(number,"date",organization_INN) VALUES (107,'2021-11-06',1005);
INSERT INTO waybill(number,"date",organization_INN) VALUES (108,'2021-11-07',1005);
INSERT INTO waybill(number,"date",organization_INN) VALUES (109,'2021-11-08',1008);
INSERT INTO waybill(number,"date",organization_INN) VALUES (110,'2021-11-09',1009);
INSERT INTO waybill(number,"date",organization_INN) VALUES (111,'2021-11-03',1009);
INSERT INTO waybill(number,"date",organization_INN) VALUES (112,'2021-11-03',1008);

INSERT INTO waybill_item(waybill_id,item_id) VALUES (101,1);
INSERT INTO waybill_item(waybill_id,item_id) VALUES (102,1);
INSERT INTO waybill_item(waybill_id,item_id) VALUES (103,2);
INSERT INTO waybill_item(waybill_id,item_id) VALUES (104,3);
INSERT INTO waybill_item(waybill_id,item_id) VALUES (105,4);
INSERT INTO waybill_item(waybill_id,item_id) VALUES (106,5);
INSERT INTO waybill_item(waybill_id,item_id) VALUES (107,6);
INSERT INTO waybill_item(waybill_id,item_id) VALUES (108,7);
INSERT INTO waybill_item(waybill_id,item_id) VALUES (108,7);
INSERT INTO waybill_item(waybill_id,item_id) VALUES (108,8);
INSERT INTO waybill_item(waybill_id,item_id) VALUES (111,1);
INSERT INTO waybill_item(waybill_id,item_id) VALUES (112,1);