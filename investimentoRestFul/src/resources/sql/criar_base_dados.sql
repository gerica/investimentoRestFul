-- Database: investimentorestful
-- DROP DATABASE investimentorestful_prod;
CREATE DATABASE investimentorestful__prod
  WITH OWNER = rogerio
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'pt_BR.UTF-8'
       LC_CTYPE = 'pt_BR.UTF-8'
       CONNECTION LIMIT = -1;
       
-- Table: public.tb_fundamento
-- DROP TABLE public.tb_fundamento;
CREATE TABLE public.tb_fundamento
(
  id_funcamento integer NOT NULL,
  nr_crescimento double precision,
  nr_dividentoyield double precision,
  nr_liquidez2meses double precision,
  nr_liquidezcorrete double precision,
  nr_margemebit double precision,
  nr_p_l double precision,
  nr_p_vp double precision,
  nr_roe double precision,
  CONSTRAINT tb_fundamento_pkey PRIMARY KEY (id_funcamento)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tb_fundamento
  OWNER TO postgres;

-- Table: public.tb_papel
-- DROP TABLE public.tb_papel;
CREATE TABLE public.tb_papel
(
  id_papel integer NOT NULL,
  in_ativo boolean,
  ds_nome character varying(255),
  ds_sigla_papel character varying(255),
  nr_setor integer,
  id_fundamento integer NOT NULL,
  CONSTRAINT tb_papel_pkey PRIMARY KEY (id_papel),
  CONSTRAINT fk_otqh6ynjj135bg2fkq1ri9f24 FOREIGN KEY (id_fundamento)
      REFERENCES public.tb_fundamento (id_funcamento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkdcf15o3dppcei3nxq0ojbjdum FOREIGN KEY (id_fundamento)
      REFERENCES public.tb_fundamento (id_funcamento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tb_papel
  OWNER TO postgres;

-- Table: public.tb_usuario
-- DROP TABLE public.tb_usuario;
CREATE TABLE public.tb_usuario
(
  id_usuario bigint NOT NULL,
  ds_authorities character varying(255),
  ds_email character varying(255),
  ds_senha character varying(255),
  ds_nome character varying(255),
  CONSTRAINT tb_usuario_pkey PRIMARY KEY (id_usuario)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tb_usuario
  OWNER TO rogerio;
  
-- Table: public.tb_configuracao_analise_cotacoes
-- DROP TABLE public.tb_configuracao_analise_cotacoes;
CREATE TABLE public.tb_configuracao_analise_cotacoes
(
  id_configuracao_analise_cotacoes integer NOT NULL,
  nr_qtd_dias_apresentar_cotacoes integer,
  nr_qtd_dias_calculo_stop_loss integer,
  nr_qtd_dias_calculo_stop_win integer,
  nr_risco_stop_loss integer,
  nr_risco_stop_win integer,
  id_usuario bigint NOT NULL,
  CONSTRAINT tb_configuracao_analise_cotacoes_pkey PRIMARY KEY (id_configuracao_analise_cotacoes),
  CONSTRAINT fk6w6fnuxtoj6hrvl9hfbkdf0gu FOREIGN KEY (id_usuario)
      REFERENCES public.tb_usuario (id_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tb_configuracao_analise_cotacoes
  OWNER TO rogerio;

-- Table: public.tb_cotacao
-- DROP TABLE public.tb_cotacao;
CREATE TABLE public.tb_cotacao
(
  id_cotacao integer NOT NULL,
  nr_abertura double precision,
  dt_cotacao timestamp without time zone,
  nr_fechamento double precision,
  nr_maxima double precision,
  nr_minima double precision,
  id_papel integer NOT NULL,
  CONSTRAINT tb_cotacao_pkey PRIMARY KEY (id_cotacao),
  CONSTRAINT fkfdsj7h2fumb1f6cl3ppnu47aj FOREIGN KEY (id_papel)
      REFERENCES public.tb_papel (id_papel) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tb_cotacao
  OWNER TO rogerio;

-- Table: public.tb_operacao_entrada
-- DROP TABLE public.tb_operacao_entrada;
CREATE TABLE public.tb_operacao_entrada
(
  id_operacao_entrada integer NOT NULL,
  ativo boolean,
  nr_avaliacao_entrada double precision,
  dt_operacao_entrada timestamp without time zone,
  nr_despesa double precision,
  ds_observacao character varying(255),
  nr_preco_unitario double precision,
  nr_quantidade integer,
  nr_stop_loss double precision,
  nr_stop_win double precision,
  ds_tipo_operacao character varying(255),
  id_papel integer NOT NULL,
  CONSTRAINT tb_operacao_entrada_pkey PRIMARY KEY (id_operacao_entrada),
  CONSTRAINT fkscwg4get7b1p19l7q9i829sqp FOREIGN KEY (id_papel)
      REFERENCES public.tb_papel (id_papel) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tb_operacao_entrada
  OWNER TO rogerio;

-- Table: public.tb_operacao_saida
-- DROP TABLE public.tb_operacao_saida;
CREATE TABLE public.tb_operacao_saida
(
  id_operacao_saida integer NOT NULL,
  in_ativo boolean,
  nr_avaliacao_saida double precision,
  dt_operacao_saida timestamp without time zone,
  nr_despesa double precision,
  ds_observacao character varying(255),
  nr_preco_unitario double precision,
  nr_quantidade integer,
  id_operacao_entrada integer NOT NULL,
  CONSTRAINT tb_operacao_saida_pkey PRIMARY KEY (id_operacao_saida),
  CONSTRAINT fkijg03gxwwwumfee5oc0d57ayn FOREIGN KEY (id_operacao_entrada)
      REFERENCES public.tb_operacao_entrada (id_operacao_entrada) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tb_operacao_saida
  OWNER TO rogerio;

-- Table: public.tb_usuario_operacao
-- DROP TABLE public.tb_usuario_operacao;
CREATE TABLE public.tb_usuario_operacao
(
  id_usuario_operacao integer NOT NULL,
  dt_usuario_operacao timestamp without time zone,
  id_operacao_entrada integer NOT NULL,
  id_usuario bigint NOT NULL,
  CONSTRAINT tb_usuario_operacao_pkey PRIMARY KEY (id_usuario_operacao),
  CONSTRAINT fk9wixj74eaabjycuhdvkel0h57 FOREIGN KEY (id_operacao_entrada)
      REFERENCES public.tb_operacao_entrada (id_operacao_entrada) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkr8au96upll5bgxejv0y7jk6am FOREIGN KEY (id_usuario)
      REFERENCES public.tb_usuario (id_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tb_usuario_operacao
  OWNER TO rogerio;

 -- Sequence: public.hibernate_sequence
-- DROP SEQUENCE public.hibernate_sequence;
CREATE SEQUENCE public.hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 2486
  CACHE 1;
ALTER TABLE public.hibernate_sequence
  OWNER TO postgres;