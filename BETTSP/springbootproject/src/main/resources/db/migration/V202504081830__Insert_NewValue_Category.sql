DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'unique_category_name'
    ) THEN
        ALTER TABLE public.category
        ADD CONSTRAINT unique_category_name UNIQUE (name);
    END IF;
END$$;

INSERT INTO public.category (id, name)
VALUES (6, 'reference')
ON CONFLICT (name) DO NOTHING;