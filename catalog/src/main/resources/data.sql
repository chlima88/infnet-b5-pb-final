-- DELETE FROM "vendor";
INSERT INTO "vendor" (vendor_id, "name", "created_at" )
VALUES ('3eac127c-95db-4d97-ad3d-b3e87a86e721', 'Vendor 1', CURRENT_TIMESTAMP);
INSERT INTO "vendor" (vendor_id, "name", "created_at" )
VALUES ('b6333aa6-265b-4bde-b27d-01797265c435', 'Vendor 2', CURRENT_TIMESTAMP);
INSERT INTO "vendor" (vendor_id, "name", "created_at" )
VALUES ('236867c2-c2aa-4551-b964-55acc3f01d4e', 'Vendor 3', CURRENT_TIMESTAMP);
-- select * from "vendor";

-- DELETE FROM "product";
INSERT INTO "product" (product_id, category, name, price, vendor_id)
VALUES ('9c2a1424-450f-47ab-9203-dd868edd1e70', 'Category X', 'Product 1', 250.00, '3eac127c-95db-4d97-ad3d-b3e87a86e721');
INSERT INTO "product" (product_id, category, name, price, vendor_id)
VALUES (gen_random_uuid(), 'Category Y', 'Product 2', 10.00, '3eac127c-95db-4d97-ad3d-b3e87a86e721');
INSERT INTO "product" (product_id, category, name, price, vendor_id)
VALUES (gen_random_uuid(), 'Category Z', 'Product 3', 1500.00, 'b6333aa6-265b-4bde-b27d-01797265c435');
INSERT INTO "product" (product_id, category, name, price, vendor_id)
VALUES (gen_random_uuid(), 'Category X', 'Product 4', 330.00, 'b6333aa6-265b-4bde-b27d-01797265c435');
INSERT INTO "product" (product_id, category, name, price, vendor_id)
VALUES (gen_random_uuid(), 'Category Y', 'Product 5', 520.00, '236867c2-c2aa-4551-b964-55acc3f01d4e');

-- DELETE FROM "cart_item"
-- DELETE FROM "cart"
INSERT INTO "cart" (cart_id, customer_id) VALUES ('6fbb691f-2fa9-429d-9bab-4313cab9cdea', '123123');
INSERT INTO "cart" (cart_id, customer_id) VALUES ('b011d253-6dbb-40c3-8937-c74929f30daa', '56456');