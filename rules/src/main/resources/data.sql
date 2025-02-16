-- Inserir dados na tabela Rules
INSERT INTO
    rules (description, created)
VALUES
    ('RULE1', CURRENT_DATE),
    ('RULE2', CURRENT_DATE);

SET @rules_id_f = 1;
SET @rules_id_s = 2;

INSERT INTO
    rule_item (description, rules_id)
VALUES
    ('OCCUPATION', @rules_id_f),
    ('ADDRESS', @rules_id_f),
    ('ADDRESS', @rules_id_s);
