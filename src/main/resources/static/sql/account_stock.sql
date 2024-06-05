CREATE TABLE Account_Stock
(
    account_id    INT REFERENCES Account (id) ON DELETE CASCADE,
    stock_id      INT REFERENCES Stock (id) ON DELETE CASCADE,
    quantity_held INT CHECK (0 <= quantity_held),
    PRIMARY KEY (account_id, stock_id)
);

INSERT INTO Account_Stock (account_id, stock_id, quantity_held)
VALUES (1, 1, 10);

CREATE OR REPLACE FUNCTION delete_row_if_quantity_is_zero() RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.quantity_held = 0 THEN
        DELETE FROM Account_Stock WHERE account_id = NEW.account_id AND stock_id = NEW.stock_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_field_before_update
    AFTER UPDATE
    ON Account_Stock
    FOR EACH ROW
    WHEN (NEW.quantity_held IS DISTINCT FROM OLD.quantity_held)
EXECUTE FUNCTION delete_row_if_quantity_is_zero();