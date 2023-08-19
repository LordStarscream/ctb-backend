--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: account; Type: TABLE; Schema: public; Owner: appuser
--

CREATE TABLE public.account (
    id bigint NOT NULL,
    location character varying(255),
    value double precision,
    reference_currency_ticker character varying(255)
);


ALTER TABLE public.account OWNER TO appuser;

--
-- Name: account_id_seq; Type: SEQUENCE; Schema: public; Owner: appuser
--

CREATE SEQUENCE public.account_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.account_id_seq OWNER TO appuser;

--
-- Name: account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: appuser
--

ALTER SEQUENCE public.account_id_seq OWNED BY public.account.id;


--
-- Name: currency; Type: TABLE; Schema: public; Owner: appuser
--

CREATE TABLE public.currency (
    ticker character varying(255) NOT NULL,
    name character varying(255)
);


ALTER TABLE public.currency OWNER TO appuser;

--
-- Name: customer; Type: TABLE; Schema: public; Owner: appuser
--

CREATE TABLE public.customer (
    id bigint NOT NULL,
    email character varying(255),
    name character varying(255)
);


ALTER TABLE public.customer OWNER TO appuser;

--
-- Name: customer_seq; Type: SEQUENCE; Schema: public; Owner: appuser
--

CREATE SEQUENCE public.customer_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.customer_seq OWNER TO appuser;

--
-- Name: fiat_exchange_rate; Type: TABLE; Schema: public; Owner: appuser
--

CREATE TABLE public.fiat_exchange_rate (
    id bigint NOT NULL,
    date timestamp(6) without time zone,
    factor double precision,
    crypto_currency_ticker character varying(255),
    exchange_name character varying(255),
    fiat_currency_ticker character varying(255)
);


ALTER TABLE public.fiat_exchange_rate OWNER TO appuser;

--
-- Name: fiat_exchange_rate_id_seq; Type: SEQUENCE; Schema: public; Owner: appuser
--

CREATE SEQUENCE public.fiat_exchange_rate_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.fiat_exchange_rate_id_seq OWNER TO appuser;

--
-- Name: fiat_exchange_rate_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: appuser
--

ALTER SEQUENCE public.fiat_exchange_rate_id_seq OWNED BY public.fiat_exchange_rate.id;


--
-- Name: fiat_exchange_rate_id_seq1; Type: SEQUENCE; Schema: public; Owner: appuser
--

ALTER TABLE public.fiat_exchange_rate ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.fiat_exchange_rate_id_seq1
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: gain; Type: TABLE; Schema: public; Owner: appuser
--

CREATE TABLE public.gain (
    id bigint NOT NULL,
    ammount double precision,
    costbasis double precision,
    in_date_time timestamp(6) without time zone,
    out_date_time timestamp(6) without time zone,
    proceeds double precision,
    profit double precision,
    short_long character varying(255),
    buy_at_name character varying(255),
    currency_ticker character varying(255),
    sell_at_name character varying(255)
);


ALTER TABLE public.gain OWNER TO appuser;

--
-- Name: gain_id_seq; Type: SEQUENCE; Schema: public; Owner: appuser
--

CREATE SEQUENCE public.gain_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.gain_id_seq OWNER TO appuser;

--
-- Name: gain_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: appuser
--

ALTER SEQUENCE public.gain_id_seq OWNED BY public.gain.id;


--
-- Name: hold; Type: TABLE; Schema: public; Owner: appuser
--

CREATE TABLE public.hold (
    id bigint NOT NULL,
    ammount double precision,
    date_time timestamp(6) without time zone,
    factor double precision,
    in_currency_ticker character varying(255),
    location_name character varying(255)
);


ALTER TABLE public.hold OWNER TO appuser;

--
-- Name: hold_id_seq; Type: SEQUENCE; Schema: public; Owner: appuser
--

CREATE SEQUENCE public.hold_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hold_id_seq OWNER TO appuser;

--
-- Name: hold_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: appuser
--

ALTER SEQUENCE public.hold_id_seq OWNED BY public.hold.id;


--
-- Name: income; Type: TABLE; Schema: public; Owner: appuser
--

CREATE TABLE public.income (
    id bigint NOT NULL,
    cost_base double precision,
    cost_base_calculation character varying(255),
    ammount double precision,
    out_date_time timestamp(6) without time zone,
    type smallint,
    worth_at_out double precision,
    in_date_time timestamp(6) without time zone,
    info character varying(255),
    worth_at_income double precision,
    currency_ticker character varying(255),
    out_at_name character varying(255),
    in_at_name character varying(255)
);


ALTER TABLE public.income OWNER TO appuser;

--
-- Name: income_id_seq; Type: SEQUENCE; Schema: public; Owner: appuser
--

CREATE SEQUENCE public.income_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.income_id_seq OWNER TO appuser;

--
-- Name: income_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: appuser
--

ALTER SEQUENCE public.income_id_seq OWNED BY public.income.id;


--
-- Name: location; Type: TABLE; Schema: public; Owner: appuser
--

CREATE TABLE public.location (
    name character varying(255) NOT NULL,
    information character varying(255),
    is_exchange boolean
);


ALTER TABLE public.location OWNER TO appuser;

--
-- Name: trades; Type: TABLE; Schema: public; Owner: appuser
--

CREATE TABLE public.trades (
    id bigint NOT NULL,
    buy_fee double precision,
    buy_value double precision,
    buying_time timestamp(6) without time zone,
    comment character varying(255),
    sell_fee double precision,
    sell_value double precision,
    selling_time timestamp(6) without time zone,
    status character varying(255),
    stop_loss double precision,
    trading_type character varying(255),
    value double precision,
    buy_currency_ticker character varying(255),
    buy_fiat_exchange_id bigint,
    currency_ticker character varying(255),
    exchange_name character varying(255),
    sell_currency_ticker character varying(255),
    sell_fiat_exchange_id bigint,
    trade_fiat_exchange_id bigint
);


ALTER TABLE public.trades OWNER TO appuser;

--
-- Name: trades_id_seq; Type: SEQUENCE; Schema: public; Owner: appuser
--

CREATE SEQUENCE public.trades_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.trades_id_seq OWNER TO appuser;

--
-- Name: trades_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: appuser
--

ALTER SEQUENCE public.trades_id_seq OWNED BY public.trades.id;


--
-- Name: trades_images; Type: TABLE; Schema: public; Owner: appuser
--

CREATE TABLE public.trades_images (
    trade_id bigint NOT NULL,
    images_id bigint NOT NULL
);


ALTER TABLE public.trades_images OWNER TO appuser;

--
-- Name: trading_images; Type: TABLE; Schema: public; Owner: appuser
--

CREATE TABLE public.trading_images (
    id bigint NOT NULL,
    address character varying(255),
    image_time timestamp(6) without time zone,
    name character varying(255)
);


ALTER TABLE public.trading_images OWNER TO appuser;

--
-- Name: trading_images_id_seq; Type: SEQUENCE; Schema: public; Owner: appuser
--

CREATE SEQUENCE public.trading_images_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.trading_images_id_seq OWNER TO appuser;

--
-- Name: trading_images_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: appuser
--

ALTER SEQUENCE public.trading_images_id_seq OWNED BY public.trading_images.id;


--
-- Name: transaction; Type: TABLE; Schema: public; Owner: appuser
--

CREATE TABLE public.transaction (
    id bigint NOT NULL,
    comment character varying(255),
    date_time timestamp(6) without time zone,
    fee double precision,
    in_value double precision,
    out_value double precision,
    type character varying(255),
    exchange_name character varying(255),
    fee_currency_ticker character varying(255),
    in_currency_ticker character varying(255),
    in_fiat_exchange_id bigint,
    out_currency_ticker character varying(255),
    out_fiat_exchange_id bigint,
    trade_id bigint
);


ALTER TABLE public.transaction OWNER TO appuser;

--
-- Name: transaction_id_seq; Type: SEQUENCE; Schema: public; Owner: appuser
--

CREATE SEQUENCE public.transaction_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.transaction_id_seq OWNER TO appuser;

--
-- Name: transaction_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: appuser
--

ALTER SEQUENCE public.transaction_id_seq OWNED BY public.transaction.id;


--
-- Name: transaction_id_seq1; Type: SEQUENCE; Schema: public; Owner: appuser
--

ALTER TABLE public.transaction ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.transaction_id_seq1
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: transaction_import; Type: TABLE; Schema: public; Owner: appuser
--

CREATE TABLE public.transaction_import (
    id bigint NOT NULL,
    comment character varying(255),
    date_time timestamp(6) without time zone,
    exchange character varying(255),
    fee double precision,
    fee_currency character varying(255),
    in_currency character varying(255),
    in_value double precision,
    out_currency character varying(255),
    out_value double precision,
    type character varying(255),
    in_fiat_exchange_id bigint,
    out_fiat_exchange_id bigint
);


ALTER TABLE public.transaction_import OWNER TO appuser;

--
-- Name: transaction_import_id_seq; Type: SEQUENCE; Schema: public; Owner: appuser
--

CREATE SEQUENCE public.transaction_import_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.transaction_import_id_seq OWNER TO appuser;

--
-- Name: transaction_import_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: appuser
--

ALTER SEQUENCE public.transaction_import_id_seq OWNED BY public.transaction_import.id;


--
-- Name: account id; Type: DEFAULT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.account ALTER COLUMN id SET DEFAULT nextval('public.account_id_seq'::regclass);


--
-- Name: gain id; Type: DEFAULT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.gain ALTER COLUMN id SET DEFAULT nextval('public.gain_id_seq'::regclass);


--
-- Name: hold id; Type: DEFAULT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.hold ALTER COLUMN id SET DEFAULT nextval('public.hold_id_seq'::regclass);


--
-- Name: income id; Type: DEFAULT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.income ALTER COLUMN id SET DEFAULT nextval('public.income_id_seq'::regclass);


--
-- Name: trades id; Type: DEFAULT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.trades ALTER COLUMN id SET DEFAULT nextval('public.trades_id_seq'::regclass);


--
-- Name: trading_images id; Type: DEFAULT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.trading_images ALTER COLUMN id SET DEFAULT nextval('public.trading_images_id_seq'::regclass);


--
-- Name: transaction_import id; Type: DEFAULT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.transaction_import ALTER COLUMN id SET DEFAULT nextval('public.transaction_import_id_seq'::regclass);


--
-- Data for Name: account; Type: TABLE DATA; Schema: public; Owner: appuser
--

COPY public.account (id, location, value, reference_currency_ticker) FROM stdin;
\.


--
-- Data for Name: currency; Type: TABLE DATA; Schema: public; Owner: appuser
--

COPY public.currency (ticker, name) FROM stdin;
ADA	Cardano
AMP	Amp
BAT	Basic Attention Token
BCH	Bitcoin Cash
BNB	Binance Coin
BTC	Bitcoin
BTCP	Bitcoin Private
DAI	Dai
EOS	EOS
ETH	Ethereum
EUR	Euro
GAS	Gas
GRT	The Graph
LTC	Litecoin
NEO	NEO
SVD	Savedroid
USD	unified Stable Dollar
USDT	USD Tether
XLM	Stellar
XRP	XRP
XVG	Verge
ZCL	Zclassic
ZEC	Zcash
ZRX	0x
\.


--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: appuser
--

COPY public.customer (id, email, name) FROM stdin;
\.


--
-- Data for Name: fiat_exchange_rate; Type: TABLE DATA; Schema: public; Owner: appuser
--

COPY public.fiat_exchange_rate (id, date, factor, crypto_currency_ticker, exchange_name, fiat_currency_ticker) FROM stdin;
1	2018-01-15 11:55:50	201.15911583874012	LTC	Coinbase	EUR
2	2018-01-15 11:56:33	1123.8778894085724	ETH	Coinbase	EUR
3	2018-01-15 12:01:15	11348.01	BTC	Coinbase	EUR
4	2018-01-15 15:32:54	1072.83	ETH	Binance	EUR
5	2018-01-15 15:32:54	0.09832054276	XVG	Binance	EUR
6	2018-01-15 15:37:17	1072.86	ETH	Binance	EUR
7	2018-01-15 15:37:17	1.487815	XRP	Binance	EUR
8	2018-01-16 12:13:50	933.255080517002	ETH	Coinbase	EUR
9	2018-01-16 19:15:50	852.84	ETH	Binance	EUR
10	2018-01-16 19:15:50	0.071441	XVG	Binance	EUR
11	2018-01-16 20:00:20	861.36	ETH	Binance	EUR
12	2018-01-16 20:00:20	0.499394	ADA	Binance	EUR
13	2018-01-16 21:05:22	885.8	ETH	Binance	EUR
14	2018-01-16 21:05:22	1.041982	XRP	Binance	EUR
15	2018-01-19 20:38:22	163.24922374177865	LTC	Coinbase	EUR
16	2018-01-19 20:38:58	884.4391614731367	ETH	Coinbase	EUR
17	2018-02-05 16:16:37	585.2708250904034	ETH	Coinbase	EUR
18	2018-02-09 16:35:43	676.9438670600166	EUR	Coinbase	ETH
19	2018-02-21 19:06:03	688.4207248563555	ETH	Coinbase	EUR
20	2018-02-21 21:32:11	172.6896540613368	LTC	Coinbase	EUR
21	2018-02-21 21:35:55	175.51945043230688	LTC	Coinbase	EUR
22	2018-02-23 16:44:28	8557.57459167105	BTC	Coinbase	EUR
23	2018-02-24 19:10:03	7887.57	BTC	Cryptopia	EUR
24	2018-02-24 19:10:03	97.52	ZCL	Cryptopia	EUR
25	2018-02-24 19:14:03	7913.24	BTC	Cryptopia	EUR
26	2018-02-24 19:14:03	101.38	ZCL	Cryptopia	EUR
27	2018-02-24 19:14:20	7912.97	BTC	Cryptopia	EUR
28	2018-02-24 19:14:20	101.38	ZCL	Cryptopia	EUR
29	2018-02-27 10:59:44	8913.575928781242	BTC	Coinbase	EUR
30	2018-03-03 13:02:06	0.036812	BTCP	Home Z Hardfork BTCP	EUR
31	2018-03-08 22:50:27	601.1669106622808	ETH	Coinbase	EUR
32	2018-03-08 23:14:42	597.5863226537595	ETH	Coinbase	EUR
33	2018-03-12 08:42:08	0.656485	XRP	Binance	EUR
34	2018-03-12 08:42:08	589.53	ETH	Binance	EUR
35	2018-03-12 09:21:03	588.4	ETH	Binance	EUR
36	2018-03-12 09:21:03	74	NEO	Binance	EUR
37	2018-03-25 11:19:24	0.005005005005005005	SVD	ico.savedroid.com	EUR
38	2018-04-02 13:05:51	6004.842648245411	BTC	Coinbase	EUR
39	2018-04-03 18:06:12	6323.0656582922575	BTC	Coinbase	EUR
40	2018-04-05 16:47:17	12.36	GAS	Binance	EUR
41	2018-04-06 09:19:45	12.39	GAS	NEON home Wallet	EUR
42	2018-04-15 06:09:47	0.075431	XVG	Binance	EUR
43	2018-04-15 06:09:47	415.32	ETH	Binance	EUR
44	2018-04-15 11:03:00	414.58	ETH	Home Wallet ETH	EUR
45	2018-04-16 06:19:42	6478.9262051366095	EUR	Coinbase	BTC
46	2018-04-23 21:12:13	20.8	GAS	NEON home Wallet	EUR
47	2018-05-03 09:38:54	24.77	GAS	Binance	EUR
48	2018-05-13 12:26:26	597.8245949131634	ETH	Coinbase	EUR
49	2018-05-16 07:09:52	566.0143346321072	EUR	Coinbase	ETH
50	2018-05-21 08:50:05	18.71	BTCP	Home B Hardfork BTCP	EUR
51	2018-05-27 17:00:18	499.6475153328498	ETH	Coinbase	EUR
52	2018-05-28 22:36:17	100.10055231096813	LTC	Coinbase	EUR
53	2018-06-02 03:47:01	17.48	GAS	Binance	EUR
54	2018-06-14 19:14:07	90.12915525974553	LTC	Coinbase	EUR
55	2018-07-02 17:03:05	426.3965709324213	ETH	Coinbase	EUR
56	2018-07-03 04:00:56	9.804565	GAS	Binance	EUR
57	2018-07-24 09:31:52	0.396601	XRP	Binance	EUR
58	2018-07-24 09:31:52	10.31	BNB	Binance	EUR
59	2018-07-24 09:31:52	8.813808	GAS	Binance	EUR
60	2018-07-24 09:31:52	10.38	BNB	Binance	EUR
61	2018-07-24 09:31:53	0.019244	XVG	Binance	EUR
62	2018-07-24 09:31:53	10.03	BNB	Binance	EUR
63	2018-08-03 12:31:38	7.375015	GAS	Binance	EUR
64	2018-08-07 11:58:01	366.2685532603117	ETH	Coinbase	EUR
65	2018-09-05 08:19:05	7.50697	GAS	Binance	EUR
66	2018-09-15 01:32:38	180.5566999970993	EUR	Coinbase Pro	ETH
67	2018-09-15 01:32:38	180.48691000067913	EUR	Coinbase Pro	ETH
68	2018-09-16 06:03:07	46.9587	EUR	Coinbase Pro	LTC
69	2018-09-16 23:36:10	188.31335999961087	EUR	Coinbase Pro	ETH
70	2018-09-17 06:34:04	5562.861199999999	EUR	Coinbase Pro	BTC
71	2018-09-17 06:34:04	5562.861199931868	EUR	Coinbase Pro	BTC
72	2018-09-17 10:06:51	47.855999999523895	EUR	Coinbase Pro	LTC
73	2018-09-17 10:06:51	47.856	EUR	Coinbase Pro	LTC
74	2018-09-17 10:06:51	47.856	EUR	Coinbase Pro	LTC
75	2018-09-17 10:06:51	47.855999999999995	EUR	Coinbase Pro	LTC
76	2018-09-17 10:06:51	47.85599999831313	EUR	Coinbase Pro	LTC
77	2018-10-03 20:51:17	5.040552	GAS	Binance	EUR
78	2018-11-02 14:36:16	4.435389	GAS	Binance	EUR
79	2018-11-16 19:56:34	37.33166000008643	LTC	Coinbase Pro	EUR
80	2018-11-16 19:57:14	4861.290249984143	BTC	Coinbase Pro	EUR
81	2018-11-18 11:33:42	36.9304599999937	LTC	Coinbase Pro	EUR
82	2018-11-19 14:18:52	32.322740000035076	EUR	Coinbase Pro	LTC
83	2018-11-19 16:06:32	4477.21145999255	BTC	Coinbase Pro	EUR
84	2018-11-19 16:37:41	32.63762000297221	LTC	Coinbase Pro	EUR
85	2018-11-19 16:37:41	32.637619999425766	LTC	Coinbase Pro	EUR
86	2018-11-19 16:37:41	32.68776999947827	LTC	Coinbase Pro	EUR
87	2018-11-19 16:37:41	32.70783	LTC	Coinbase Pro	EUR
88	2018-11-19 16:37:41	32.73792	LTC	Coinbase Pro	EUR
89	2018-11-19 16:37:41	32.737920000103024	LTC	Coinbase Pro	EUR
90	2018-11-19 16:37:41	32.798099999861044	LTC	Coinbase Pro	EUR
91	2018-11-23 10:34:46	109.27685000032157	ETH	Coinbase Pro	EUR
92	2018-11-23 10:34:55	28.766040000050314	LTC	Coinbase Pro	EUR
93	2018-11-23 10:34:55	28.766040000463683	LTC	Coinbase Pro	EUR
94	2018-11-23 10:34:55	28.76604	LTC	Coinbase Pro	EUR
95	2018-11-23 10:34:55	28.766040000058744	LTC	Coinbase Pro	EUR
96	2018-12-03 09:05:46	2.206122	GAS	Binance	EUR
97	2019-01-02 13:38:38	1.983781	GAS	Binance	EUR
98	2019-01-04 13:50:28	0.2816	ZRX	Coinbase	EUR
99	2019-01-04 13:52:14	0.2816	ZRX	Coinbase	EUR
100	2019-01-04 14:04:08	0.2816	ZRX	Coinbase	EUR
101	2019-02-01 15:12:45	1.667462	GAS	Binance	EUR
102	2019-02-09 16:40:20	38.27482999996458	EUR	Coinbase Pro	LTC
103	2019-02-09 16:40:20	38.27483	EUR	Coinbase Pro	LTC
104	2019-02-09 16:40:20	38.264859995860604	EUR	Coinbase Pro	LTC
105	2019-02-09 16:40:20	38.264860003864	EUR	Coinbase Pro	LTC
106	2019-02-09 16:40:20	38.26486000513106	EUR	Coinbase Pro	LTC
107	2019-02-09 16:40:20	38.25489000013499	EUR	Coinbase Pro	LTC
108	2019-02-09 16:41:15	38.20504000053679	EUR	Coinbase Pro	LTC
109	2019-02-09 16:41:15	38.20504000030223	EUR	Coinbase Pro	LTC
110	2019-02-09 16:41:15	38.19507	EUR	Coinbase Pro	LTC
111	2019-02-09 16:41:15	38.19507000153256	EUR	Coinbase Pro	LTC
112	2019-02-09 16:41:15	38.18510000106474	EUR	Coinbase Pro	LTC
113	2019-02-12 09:10:14	1.897882	GAS	NEON home Wallet	EUR
114	2019-02-21 08:49:39	129.5	EUR	Coinbase Pro	ETH
115	2019-02-22 10:47:33	46.69	ZEC	Coinbase	EUR
116	2019-02-22 10:48:04	46.69	ZEC	Coinbase	EUR
117	2019-02-22 10:50:25	46.69	ZEC	Coinbase	EUR
118	2019-03-04 05:33:46	2.117538	GAS	Binance	EUR
119	2019-04-03 05:57:20	2.844422	GAS	Binance	EUR
120	2019-04-09 13:10:32	4627.049000012358	EUR	Coinbase Pro	BTC
121	2019-04-09 13:10:41	4627.048999904136	EUR	Coinbase Pro	BTC
122	2019-04-09 13:10:44	4627.049000059571	EUR	Coinbase Pro	BTC
123	2019-04-09 13:10:53	4627.049000115145	EUR	Coinbase Pro	BTC
124	2019-04-12 08:55:19	2.767417	GAS	Binance	EUR
125	2019-04-12 08:55:19	15.71	BNB	Binance	EUR
126	2019-04-23 06:27:48	0.357154	BAT	Coinbase	EUR
127	2019-04-23 06:29:46	0.357154	BAT	Coinbase	EUR
128	2019-04-23 06:32:14	0.357154	BAT	Coinbase	EUR
129	2019-04-23 06:35:33	0.357154	BAT	Coinbase	EUR
130	2019-05-04 12:55:21	2.396494	GAS	Binance	EUR
131	2019-06-04 20:44:56	2.777067	GAS	Binance	EUR
132	2019-06-26 14:28:40	126.6962168699687	LTC	Coinbase	EUR
133	2019-06-27 11:12:05	91.76999999860487	EUR	Coinbase Pro	LTC
134	2019-06-27 11:12:05	91.77	EUR	Coinbase Pro	LTC
135	2019-06-27 11:12:05	91.77000000000001	EUR	Coinbase Pro	LTC
136	2019-06-27 11:12:05	91.77000000646285	EUR	Coinbase Pro	LTC
137	2019-06-27 11:12:05	91.77	EUR	Coinbase Pro	LTC
138	2019-06-27 11:12:05	91.77000000000001	EUR	Coinbase Pro	LTC
139	2019-06-27 11:12:05	91.77000000405351	EUR	Coinbase Pro	LTC
140	2019-06-27 11:12:05	91.7700000010946	EUR	Coinbase Pro	LTC
141	2019-06-27 21:38:23	5.943171	EOS	Coinbase	EUR
142	2019-06-27 22:38:14	5.943171	EOS	Coinbase	EUR
143	2019-06-27 22:40:23	5.943171	EOS	Coinbase	EUR
144	2019-06-27 22:43:29	5.943171	EOS	Coinbase	EUR
145	2019-06-27 22:46:02	5.943171	EOS	Coinbase	EUR
146	2019-06-28 10:22:08	101.58332499941679	LTC	Coinbase Pro	EUR
147	2019-06-30 16:45:13	5.3710654260221675	EOS	Coinbase	EUR
148	2019-07-04 13:44:30	2.907692	GAS	Binance	EUR
149	2019-07-11 06:17:43	89.77500000007012	EUR	Coinbase Pro	LTC
150	2019-07-12 09:56:15	0.0901758775	XLM	Coinbase Pro	EUR
151	2019-07-12 09:56:15	0.09018389750716332	XLM	Coinbase Pro	EUR
152	2019-07-14 13:52:50	0.07703792249929557	EUR	Coinbase Pro	XLM
153	2019-07-14 15:51:58	3.7905	EUR	Coinbase Pro	EOS
154	2019-07-14 15:51:58	3.7905	EUR	Coinbase Pro	EOS
155	2019-07-14 15:51:58	3.7905	EUR	Coinbase Pro	EOS
156	2019-07-14 15:51:58	3.7905	EUR	Coinbase Pro	EOS
157	2019-07-14 15:51:58	3.7905	EUR	Coinbase Pro	EOS
158	2019-07-16 22:00:15	71.5283749994762	LTC	Coinbase Pro	EUR
159	2019-07-16 22:21:06	253.57234999298768	BCH	Coinbase Pro	EUR
160	2019-07-18 10:21:35	3.5062515000000003	EOS	Coinbase Pro	EUR
161	2019-08-03 02:34:20	1.880433	GAS	Binance	EUR
162	2019-08-10 21:51:00	0.890073	DAI	Coinbase	EUR
163	2019-08-10 21:53:38	0.890073	DAI	Coinbase	EUR
164	2019-08-10 21:56:12	0.890073	DAI	Coinbase	EUR
165	2019-08-10 22:10:10	0.891473	DAI	Coinbase	EUR
166	2019-08-13 18:06:58	76.45064998319543	LTC	Coinbase Pro	EUR
167	2019-08-13 18:06:58	76.47069999754682	LTC	Coinbase Pro	EUR
168	2019-08-14 18:27:48	68.35867498768974	EUR	Coinbase Pro	LTC
169	2019-08-14 18:27:48	68.35867499802522	EUR	Coinbase Pro	LTC
170	2019-08-19 00:24:34	69.8642250019755	LTC	Coinbase Pro	EUR
171	2019-08-19 00:24:34	69.91435000350714	LTC	Coinbase Pro	EUR
172	2019-08-19 00:51:39	183.46039978987056	ETH	Coinbase	EUR
173	2019-08-21 14:25:43	63.8000999980708	EUR	Coinbase Pro	LTC
174	2019-08-21 14:31:18	165.58492975734356	EUR	Coinbase Pro	ETH
175	2019-08-21 14:31:18	165.45532500650967	EUR	Coinbase Pro	ETH
176	2019-08-21 14:31:18	165.45532502455433	EUR	Coinbase Pro	ETH
177	2019-08-23 16:08:28	178.5352250074294	ETH	Coinbase Pro	EUR
178	2019-08-28 20:02:22	2.89275	EUR	Coinbase Pro	EOS
179	2019-08-28 20:02:22	2.882775	EUR	Coinbase Pro	EOS
180	2019-08-28 20:02:22	2.874795	EUR	Coinbase Pro	EOS
181	2019-08-28 20:14:24	59.181818181818194	EUR	Coinbase Pro	LTC
182	2019-08-28 20:14:24	59.161725002042296	EUR	Coinbase Pro	LTC
183	2019-08-29 20:39:39	2.9283025	EOS	Coinbase Pro	EUR
184	2019-09-01 21:37:57	60.65125	LTC	Coinbase Pro	EUR
185	2019-09-04 16:12:41	1.308008	GAS	Binance	EUR
186	2019-09-09 11:42:17	3.504735	EUR	Coinbase Pro	EOS
187	2019-09-19 22:43:19	287.2002000009678	EUR	Coinbase Pro	BCH
188	2019-09-19 22:43:33	68.50934579439253	EUR	Coinbase Pro	LTC
189	2019-09-19 22:43:33	68.48835	EUR	Coinbase Pro	LTC
190	2019-09-19 22:43:33	68.44844999968299	EUR	Coinbase Pro	LTC
191	2019-09-20 13:09:38	194.3529	EUR	Coinbase Pro	ETH
192	2019-09-23 14:42:05	278.53459997395515	BCH	Coinbase Pro	EUR
193	2019-09-23 14:42:05	278.5345999911184	BCH	Coinbase Pro	EUR
194	2019-09-23 21:09:17	65.88429999774092	LTC	Coinbase Pro	EUR
195	2019-09-23 21:09:17	65.8943250186506	LTC	Coinbase Pro	EUR
196	2019-09-23 22:59:17	63.620550000692745	EUR	Coinbase Pro	LTC
197	2019-09-23 23:18:43	268.3274999974825	EUR	Coinbase Pro	BCH
198	2019-09-26 12:40:27	52.460825001190955	LTC	Coinbase Pro	EUR
199	2019-09-26 18:22:55	47.66054999500885	EUR	Coinbase Pro	LTC
200	2019-09-26 18:22:55	47.650575002165986	EUR	Coinbase Pro	LTC
201	2019-09-26 18:22:55	47.63062500851939	EUR	Coinbase Pro	LTC
202	2019-09-27 08:33:17	50.255325005005005	LTC	Coinbase Pro	EUR
203	2019-09-27 08:33:17	50.2653499976563	LTC	Coinbase Pro	EUR
204	2019-09-27 08:43:37	196.27947499467405	BCH	Coinbase Pro	EUR
205	2019-10-02 20:22:32	1.135324	GAS	Binance	EUR
206	2019-10-23 14:41:52	45.885000000505485	EUR	Coinbase Pro	LTC
207	2019-10-23 14:41:52	45.884999998759184	EUR	Coinbase Pro	LTC
208	2019-10-23 17:56:51	185.7444749969388	EUR	Coinbase Pro	BCH
209	2019-11-05 13:00:19	1.44208	GAS	Binance	EUR
210	2019-11-05 16:20:15	57.285000000000004	LTC	Coinbase Pro	EUR
211	2019-11-05 16:20:15	57.30509999970319	LTC	Coinbase Pro	EUR
212	2019-11-15 15:16:50	51.9987	EUR	Coinbase Pro	LTC
213	2019-11-15 15:16:50	51.93899999084239	EUR	Coinbase Pro	LTC
214	2019-11-22 09:06:50	146.69984999601766	ETH	Coinbase Pro	EUR
215	2019-11-22 09:07:00	46.4812499842312	LTC	Coinbase Pro	EUR
216	2019-11-22 09:07:00	46.531499998436864	LTC	Coinbase Pro	EUR
217	2019-11-22 09:07:08	205.36169999236844	BCH	Coinbase Pro	EUR
218	2019-11-22 09:07:17	6947.524799991449	BTC	Coinbase Pro	EUR
219	2019-11-22 11:04:05	42.47058823529412	EUR	Coinbase Pro	LTC
220	2019-11-22 11:04:05	42.47655000171909	EUR	Coinbase Pro	LTC
221	2019-11-22 15:04:04	128.90224999654126	EUR	Coinbase Pro	ETH
222	2019-11-22 15:09:47	176.62245000204808	EUR	Coinbase Pro	BCH
223	2019-11-22 15:10:24	6169	EUR	Coinbase Pro	BTC
224	2019-11-25 17:04:27	6682.244306418218	BTC	Coinbase Pro	EUR
225	2019-11-25 17:04:27	6683.239949999999	BTC	Coinbase Pro	EUR
226	2019-11-25 17:04:27	6683.249999852899	BTC	Coinbase Pro	EUR
227	2019-11-26 04:53:44	2.406975	EOS	Coinbase Pro	EUR
228	2019-11-26 04:53:44	2.40798	EOS	Coinbase Pro	EUR
229	2019-11-27 10:33:18	6220.49125	EUR	Coinbase Pro	BTC
230	2019-11-27 16:43:08	6692.295	BTC	Coinbase Pro	EUR
231	2019-11-27 17:50:37	140.18745	ETH	Coinbase Pro	EUR
232	2019-11-27 17:50:58	44.23005	LTC	Coinbase Pro	EUR
233	2019-11-28 10:14:41	200.50755002561323	BCH	Coinbase Pro	EUR
234	2019-11-28 10:14:41	200.51759999416674	BCH	Coinbase Pro	EUR
235	2019-12-04 01:37:08	39.48199767711962	EUR	Coinbase Pro	LTC
236	2019-12-04 01:37:08	39.451750000579544	EUR	Coinbase Pro	LTC
237	2019-12-04 01:37:08	39.451749999653416	EUR	Coinbase Pro	LTC
238	2019-12-04 09:59:03	40.6522500045541	LTC	Coinbase Pro	EUR
239	2019-12-04 09:59:03	40.66230000589542	LTC	Coinbase Pro	EUR
240	2019-12-04 09:59:03	40.67234999394978	LTC	Coinbase Pro	EUR
241	2019-12-04 09:59:03	40.682400006791	LTC	Coinbase Pro	EUR
242	2019-12-05 17:05:21	0.924875	GAS	Binance	EUR
243	2019-12-16 19:29:26	121.1512	EUR	Coinbase Pro	ETH
244	2019-12-16 19:29:30	2.15119	EUR	Coinbase Pro	EOS
245	2019-12-16 19:39:58	35.52150000000239	EUR	Coinbase Pro	LTC
246	2019-12-16 19:40:13	35.52149999679124	EUR	Coinbase Pro	LTC
247	2019-12-18 13:52:59	5850.6002137894175	EUR	Coinbase Pro	BTC
248	2019-12-18 13:52:59	5850.251750892689	EUR	Coinbase Pro	BTC
249	2019-12-18 13:52:59	5849.604999852178	EUR	Coinbase Pro	BTC
250	2019-12-18 20:59:18	36.4011	LTC	Coinbase Pro	EUR
251	2019-12-18 21:18:24	6382.7549999644725	BTC	Coinbase Pro	EUR
252	2020-03-28 20:20:20.21	1.9502000000000002	EUR	Coinbase Pro	EOS
253	2020-03-28 20:20:20.241	1.9502000000000002	EUR	Coinbase Pro	EOS
254	2020-03-28 20:20:20.254	1.9501999999999997	EUR	Coinbase Pro	EOS
255	2020-03-28 20:20:20.323	1.9502000000000002	EUR	Coinbase Pro	EOS
256	2020-05-03 18:36:43.573	2.5838550000000002	EOS	Coinbase Pro	EUR
257	2020-05-03 18:36:43.573	2.5838550000000002	EOS	Coinbase Pro	EUR
258	2020-05-10 00:17:39.05	2.2795449999999997	EUR	Coinbase Pro	EOS
259	2020-06-27 20:17:55.042	2.0502000000000002	EOS	Coinbase Pro	EUR
260	2020-06-27 20:17:55.045	2.0502000000000002	EOS	Coinbase Pro	EUR
261	2020-07-30 21:38:22.38	2.5770500000000003	EUR	Coinbase Pro	EOS
262	2020-07-30 21:38:22.38	2.57705	EUR	Coinbase Pro	EOS
263	2020-01-11 07:46:31.236	241.7054	EUR	Coinbase Pro	BCH
264	2020-01-11 07:46:31.236	241.30739999999997	EUR	Coinbase Pro	BCH
265	2020-02-18 07:41:38.497	383.07585	BCH	Coinbase Pro	EUR
266	2020-02-20 16:06:18.11	335.315	EUR	Coinbase Pro	BCH
267	2020-02-20 16:06:18.11	335.315	EUR	Coinbase Pro	BCH
268	2020-02-20 16:37:23.654	337.42875	BCH	Coinbase Pro	EUR
269	2020-02-20 16:37:23.669	337.42875	BCH	Coinbase Pro	EUR
270	2020-02-26 08:26:37.111	290.45045	EUR	Coinbase Pro	BCH
271	2020-02-26 16:16:42.664	282.40500000000003	BCH	Coinbase Pro	EUR
272	2020-03-09 15:43:51.19	214.21355000000003	EUR	Coinbase Pro	BCH
273	2020-03-12 22:13:30.164	158.2875	BCH	Coinbase Pro	EUR
274	2020-03-12 23:28:42.722	131.6385	EUR	Coinbase Pro	BCH
275	2020-03-28 03:37:19.324	185.07	EUR	Coinbase Pro	BCH
276	2020-04-07 07:17:23.349	239.4312	BCH	Coinbase Pro	EUR
277	2020-04-07 14:15:48.964	232.78025000000002	EUR	Coinbase Pro	BCH
278	2020-04-07 14:15:48.982	232.63099999999997	EUR	Coinbase Pro	BCH
279	2020-04-07 14:15:49.013	232.631	EUR	Coinbase Pro	BCH
280	2020-04-09 18:47:24.113	236.175	BCH	Coinbase Pro	EUR
281	2020-04-10 04:00:23.772	221.4472	EUR	Coinbase Pro	BCH
282	2020-04-27 15:04:38.828	219.8337	BCH	Coinbase Pro	EUR
283	2020-05-03 18:31:49.2	230.77815	BCH	Coinbase Pro	EUR
284	2020-05-11 18:13:10.083	204.76105	EUR	Coinbase Pro	BCH
285	2020-05-11 18:13:10.083	204.76105	EUR	Coinbase Pro	BCH
286	2020-06-27 19:47:14.431	195.86575	EUR	Coinbase Pro	BCH
287	2020-06-27 20:03:56.671	188.4375	BCH	Coinbase Pro	EUR
288	2020-07-30 21:13:50.815	249.0087	EUR	Coinbase Pro	BCH
289	2020-08-27 17:40:14.607	221.4015	BCH	Coinbase Pro	EUR
290	2020-08-27 17:41:43.909	221.4015	BCH	Coinbase Pro	EUR
291	2020-08-27 17:44:34.316	221.4015	BCH	Coinbase Pro	EUR
292	2020-09-03 23:30:47.332	185.56750000000002	EUR	Coinbase Pro	BCH
293	2020-09-03 23:30:47.359	185.5675	EUR	Coinbase Pro	BCH
294	2020-09-03 23:30:47.362	185.5675	EUR	Coinbase Pro	BCH
295	2020-12-15 14:53:44.157	0.38994	XRP	Coinbase Pro	EUR
296	2020-12-22 23:44:05.227	0.37310129999999997	XRP	Coinbase Pro	EUR
297	2020-12-23 01:40:46.76	0.3336282	EUR	Coinbase Pro	XRP
298	2020-12-23 16:48:16.236	0.2537089	EUR	Coinbase Pro	XRP
299	2020-12-23 16:48:16.236	0.25360924999999995	EUR	Coinbase Pro	XRP
300	2020-01-14 20:25:18.541	51.292249999999996	EUR	Coinbase Pro	LTC
301	2020-01-23 17:52:13.071	47.4018	EUR	Coinbase Pro	LTC
302	2020-03-12 21:56:41.65	30.03945	LTC	Coinbase Pro	EUR
303	2020-03-12 21:56:41.65	30.049500000000002	LTC	Coinbase Pro	EUR
304	2020-03-13 01:45:32.487	25.869999999999997	EUR	Coinbase Pro	LTC
305	2020-03-13 01:45:32.487	25.869999999999997	EUR	Coinbase Pro	LTC
306	2020-03-13 01:45:32.487	25.87	EUR	Coinbase Pro	LTC
307	2020-03-13 01:45:32.487	25.87	EUR	Coinbase Pro	LTC
308	2020-03-13 01:45:32.487	25.87	EUR	Coinbase Pro	LTC
309	2020-03-13 21:10:59.022	32.9841	LTC	Coinbase Pro	EUR
310	2020-03-13 21:10:59.022	32.99415	LTC	Coinbase Pro	EUR
311	2020-03-29 05:38:18.376	34.17825	EUR	Coinbase Pro	LTC
312	2020-04-07 12:16:30.848	43.58685	LTC	Coinbase Pro	EUR
313	2020-04-07 12:16:30.848	43.5969	LTC	Coinbase Pro	EUR
314	2020-04-07 12:16:30.848	43.5969	LTC	Coinbase Pro	EUR
315	2020-04-07 20:03:05.31	41.2925	EUR	Coinbase Pro	LTC
316	2020-09-03 23:29:47.407	41.75775	LTC	Coinbase Pro	EUR
317	2020-05-10 00:20:44.94	173.38870000000003	EUR	Coinbase Pro	ETH
318	2020-05-10 00:20:44.94	173.3489	EUR	Coinbase Pro	ETH
319	2020-05-10 00:20:44.94	173.33894999999998	EUR	Coinbase Pro	ETH
320	2020-05-10 00:20:44.94	173.23945	EUR	Coinbase Pro	ETH
321	2020-05-10 00:20:44.94	173.13995	EUR	Coinbase Pro	ETH
322	2020-01-14 20:31:02.831	7792.1833	EUR	Coinbase Pro	BTC
323	2020-02-16 16:53:04.582	8829.3315	EUR	Coinbase Pro	BTC
324	2020-02-16 16:53:04.582	8828.635	EUR	Coinbase Pro	BTC
325	2020-02-20 09:56:20.852	8954.88165	BTC	Coinbase Pro	EUR
326	2020-02-20 09:56:20.852	8954.8917	BTC	Coinbase Pro	EUR
327	2020-02-25 16:48:13.698	8542.14465	EUR	Coinbase Pro	BTC
328	2020-02-26 14:23:50.422	8211.2574	EUR	Coinbase Pro	BTC
329	2020-03-01 07:10:16.733	7812.53835	BTC	Coinbase Pro	EUR
330	2020-03-09 04:10:19.868	6974.950000000001	EUR	Coinbase Pro	BTC
331	2020-12-16 13:44:22.728	16672.95	BTC	Coinbase Pro	EUR
332	2020-12-16 13:44:22.728	16683	BTC	Coinbase Pro	EUR
333	2020-12-16 13:44:22.728	16683	BTC	Coinbase Pro	EUR
334	2020-12-16 13:44:22.728	16683	BTC	Coinbase Pro	EUR
335	2020-12-16 13:44:22.728	16683	BTC	Coinbase Pro	EUR
336	2020-12-16 13:44:22.728	16683	BTC	Coinbase Pro	EUR
337	2020-12-17 09:19:30	17479.182574774823	BTC	Phemex	\N
338	2020-12-17 09:19:30	0.8186584005551784	USDT	Phemex	\N
339	2020-12-17 17:51:36	0.8186584005551784	USDT	Phemex	\N
340	2020-12-17 17:51:36	17479.182574774823	BTC	Phemex	\N
341	2020-11-25 00:12:00	0.16341022390330226	XLM	Coinbase	\N
342	2020-11-25 00:12:00	0.16341022390330226	XLM	Coinbase	\N
343	2020-11-25 00:13:00	0.16341022390330226	XLM	Coinbase	\N
344	2020-11-25 00:13:00	0.16341022390330226	XLM	Coinbase	\N
345	2020-11-25 00:12:00	0.16341022390330226	XLM	Coinbase	\N
346	2020-08-12 18:41:32	1.471041965724249	GAS	NEON home Wallet	\N
347	2020-11-26 18:12:44	1.561467343112256	GAS	NEON home Wallet	\N
348	2020-12-07 21:38:08	1.4228634301348653	GAS	NEON home Wallet	\N
349	2020-05-03 20:42:00	191.30175030720056	ETH	Coinbase	EUR
350	2020-03-13 22:22:00	158.5003670623735	BCH	Coinbase	EUR
351	2020-03-13 22:34:00	1.8743071544299479	EOS	Coinbase	EUR
352	2021-02-05 08:00:55	-1400.9562651175938	ETH	Coinbase	EUR
353	2021-08-09 08:02:03	0.8537743138646571	DAI	Coinbase	\N
354	2021-08-10 10:11:44	0.8553943272988543	DAI	Coinbase	\N
355	2021-08-11 13:49:50	0.8536221011356178	DAI	Coinbase	\N
356	2021-08-12 14:19:44	0.8510264215759197	DAI	Coinbase	\N
357	2021-08-13 14:03:45	0.8522224187877911	DAI	Coinbase	\N
358	2021-08-14 10:17:08	0.8484095846108912	DAI	Coinbase	\N
359	2021-08-15 10:04:02	0.847707214175866	DAI	Coinbase	\N
360	2021-08-16 09:27:51	0.8487571817628591	DAI	Coinbase	\N
361	2021-08-17 08:19:32	0.8499255331484287	DAI	Coinbase	\N
362	2021-08-18 13:07:15	0.8558150012079034	DAI	Coinbase	\N
363	2021-08-19 12:18:26	0.856667621307741	DAI	Coinbase	\N
364	2021-08-20 09:56:11	0.86450879133493	DAI	Coinbase	\N
365	2021-08-21 15:26:25	0.8543939360704937	DAI	Coinbase	\N
366	2021-08-23 03:46:08	0.8605958712528334	DAI	Coinbase	\N
367	2021-08-23 14:18:02	0.8605958712528334	DAI	Coinbase	\N
368	2021-08-24 09:15:01	0.8516904395642656	DAI	Coinbase	\N
369	2021-08-25 14:08:05	0.8525767129664183	DAI	Coinbase	\N
370	2021-08-26 09:15:48	0.8494285225673855	DAI	Coinbase	\N
371	2021-08-27 11:25:51	0.8504261495237487	DAI	Coinbase	\N
372	2021-08-28 14:04:33	0.8500562165161353	DAI	Coinbase	\N
373	2021-08-29 08:41:49	0.8500562165161353	DAI	Coinbase	\N
374	2021-08-30 13:54:09	0.8475146618109123	DAI	Coinbase	\N
375	2021-08-31 15:14:48	0.8458077790293868	DAI	Coinbase	\N
376	2021-09-01 12:44:10	0.8501025295246286	DAI	Coinbase	\N
377	2021-09-02 08:46:33	0.8446353256073021	DAI	Coinbase	\N
378	2021-09-03 12:32:35	0.8420187141765069	DAI	Coinbase	\N
379	2021-09-04 10:25:41	0.8408907280625164	DAI	Coinbase	\N
380	2021-09-05 11:12:54	0.8400987737230561	DAI	Coinbase	\N
381	2021-09-06 12:35:54	0.841467326812695	DAI	Coinbase	\N
382	2021-09-07 09:48:41	0.8427594533579075	DAI	Coinbase	\N
383	2021-09-08 10:49:02	0.8456542439937145	DAI	Coinbase	\N
384	2021-09-09 07:58:39	0.8495011594557913	DAI	Coinbase	\N
385	2021-09-10 10:45:17	0.8495472141694329	DAI	Coinbase	\N
386	2021-09-11 07:55:34	0.8446501165919018	DAI	Coinbase	\N
387	2021-09-12 09:29:25	0.8486013711510472	DAI	Coinbase	\N
388	2021-09-13 08:31:24	0.8558421035485726	DAI	Coinbase	\N
389	2021-09-14 12:38:44	0.8501762475727523	DAI	Coinbase	\N
390	2021-09-15 08:55:09	0.8487359237571037	DAI	Coinbase	\N
391	2021-09-16 08:37:24	0.8476722285850521	DAI	Coinbase	\N
392	2021-09-17 09:25:56	0.8517646985846651	DAI	Coinbase	\N
393	2021-09-18 11:40:11	0.8565865564733675	DAI	Coinbase	\N
394	2021-09-19 12:47:19	0.8560860584128444	DAI	Coinbase	\N
395	2021-09-21 13:32:30	0.845283043840686	DAI	Coinbase	\N
396	2021-09-22 11:55:11	0.8488981674309838	DAI	Coinbase	\N
397	2021-09-23 11:18:56	0.8560571355086839	DAI	Coinbase	\N
398	2021-09-24 13:47:47	0.8535451537123152	DAI	Coinbase	\N
399	2021-09-25 00:47:52	0.8551315381659131	DAI	Coinbase	\N
400	2021-09-25 13:08:49	0.8551315381659131	DAI	Coinbase	\N
401	2021-09-26 12:30:45	0.8568143935859582	DAI	Coinbase	\N
402	2021-09-27 12:26:31	0.8565453726586224	DAI	Coinbase	\N
403	2021-09-28 14:18:14	0.8501756442768486	DAI	Coinbase	\N
404	2021-09-29 10:32:58	0.8512684389371903	DAI	Coinbase	\N
405	2021-09-30 14:19:47	0.8652460829565649	DAI	Coinbase	\N
406	2021-10-01 11:30:07	0.8689657687864485	DAI	Coinbase	\N
407	2021-10-02 11:01:32	0.8647991590656668	DAI	Coinbase	\N
408	2021-10-03 08:59:23	0.8620424983534131	DAI	Coinbase	\N
409	2021-10-04 10:03:45	0.8645567792283257	DAI	Coinbase	\N
410	2021-10-05 11:37:57	0.8635007459823668	DAI	Coinbase	\N
411	2021-10-06 09:00:11	0.8639201334442408	DAI	Coinbase	\N
412	2021-10-07 09:01:35	0.8674650126991609	DAI	Coinbase	\N
413	2021-10-08 08:28:59	0.866143185879763	DAI	Coinbase	\N
414	2021-10-09 08:06:41	0.865969052324413	DAI	Coinbase	\N
415	2021-10-10 12:45:36	0.8668684818007671	DAI	Coinbase	\N
416	2021-10-11 15:20:57	0.8657981282557599	DAI	Coinbase	\N
417	2021-10-12 14:00:43	0.8718413339013822	DAI	Coinbase	\N
418	2021-10-13 08:48:00	0.8689752497481814	DAI	Coinbase	\N
419	2021-10-14 13:50:04	0.866334266378689	DAI	Coinbase	\N
420	2021-10-15 10:05:54	0.8644302028580123	DAI	Coinbase	\N
421	2021-10-16 10:15:24	0.8663071558029151	DAI	Coinbase	\N
422	2021-10-17 11:32:14	0.8645486721068381	DAI	Coinbase	\N
423	2021-10-18 09:12:30	0.8661338852047775	DAI	Coinbase	\N
424	2021-10-19 08:43:23	0.8617229685477397	DAI	Coinbase	\N
425	2021-10-20 12:13:53	0.8596223794175211	DAI	Coinbase	\N
426	2021-10-21 08:23:12	0.8581854098913411	DAI	Coinbase	\N
427	2021-10-22 10:27:29	0.8609768574342349	DAI	Coinbase	\N
428	2021-10-23 08:24:18	0.8636593578379458	DAI	Coinbase	\N
429	2021-10-24 14:31:04	0.8645021139228569	DAI	Coinbase	\N
430	2021-10-25 12:20:53	0.8635607067619318	DAI	Coinbase	\N
431	2021-10-26 08:36:09	0.86557817181888	DAI	Coinbase	\N
432	2021-10-27 08:20:13	0.8623902996668912	DAI	Coinbase	\N
433	2021-10-28 10:13:49	0.8613836775921345	DAI	Coinbase	\N
434	2021-10-29 12:32:35	0.8550047220004316	DAI	Coinbase	\N
435	2021-10-30 12:46:04	0.8647540613817646	DAI	Coinbase	\N
436	2021-10-31 15:47:51	0.8684340119668359	DAI	Coinbase	\N
437	2021-11-01 12:07:38	0.865201740941255	DAI	Coinbase	\N
438	2021-11-02 11:52:40	0.8634207393969896	DAI	Coinbase	\N
439	2021-11-03 11:21:25	0.8659709339325251	DAI	Coinbase	\N
440	2021-11-04 13:42:19	0.861800318173923	DAI	Coinbase	\N
441	2021-11-05 09:50:19	0.8695101972624781	DAI	Coinbase	\N
442	2021-11-06 08:36:22	0.868682470809788	DAI	Coinbase	\N
443	2021-11-07 10:31:09	0.868520195216541	DAI	Coinbase	\N
444	2021-11-08 14:30:00	0.8635563020663123	DAI	Coinbase	\N
445	2021-11-09 14:59:40	0.8638797076282314	DAI	Coinbase	\N
446	2021-11-10 14:37:38	0.8635128795296849	DAI	Coinbase	\N
447	2021-11-11 11:13:04	0.8743487993499338	DAI	Coinbase	\N
448	2021-11-12 10:59:39	0.8745765348620057	DAI	Coinbase	\N
449	2021-11-13 14:34:16	0.8805091956126558	DAI	Coinbase	\N
450	2021-11-14 08:30:45	0.8766045116617114	DAI	Coinbase	\N
451	2021-11-15 09:29:55	0.8813023934492158	DAI	Coinbase	\N
452	2021-11-16 12:14:01	0.8826925615207089	DAI	Coinbase	\N
453	2021-11-17 14:08:38	0.8856432904144201	DAI	Coinbase	\N
454	2021-11-18 09:28:08	0.8903273632529202	DAI	Coinbase	\N
455	2021-11-19 12:52:41	0.8789179048940937	DAI	Coinbase	\N
456	2021-11-20 14:10:31	0.8915193550602837	DAI	Coinbase	\N
457	2021-11-21 12:14:45	0.8918976940330068	DAI	Coinbase	\N
458	2021-11-22 12:11:08	0.8907598775045942	DAI	Coinbase	\N
459	2021-11-23 11:52:59	0.8892647325284182	DAI	Coinbase	\N
460	2021-11-24 13:16:02	0.8901818833138452	DAI	Coinbase	\N
461	2021-11-25 11:56:57	0.8945026350907715	DAI	Coinbase	\N
462	2021-11-26 11:57:29	0.8903704587469331	DAI	Coinbase	\N
463	2021-11-27 10:12:28	0.8835440571098934	DAI	Coinbase	\N
464	2021-11-28 08:42:04	0.8823307527007179	DAI	Coinbase	\N
465	2021-11-29 08:59:47	0.8872658133574494	DAI	Coinbase	\N
466	2021-11-30 14:18:31	0.8842498957736418	DAI	Coinbase	\N
467	2021-12-01 08:32:47	0.880698523972643	DAI	Coinbase	\N
468	2021-12-02 09:00:29	0.8846267175717629	DAI	Coinbase	\N
469	2021-12-03 15:09:37	0.882193405258047	DAI	Coinbase	\N
470	2021-12-04 08:40:23	0.8835601838406949	DAI	Coinbase	\N
471	2021-12-05 12:52:48	0.8835010038917137	DAI	Coinbase	\N
472	2021-12-06 12:04:06	0.8850520459571081	DAI	Coinbase	\N
473	2021-12-07 09:53:54	0.8871614008474018	DAI	Coinbase	\N
474	2021-12-08 13:03:32	0.8896939377798074	DAI	Coinbase	\N
475	2021-12-09 09:41:38	0.8826480446342552	DAI	Coinbase	\N
476	2021-12-10 13:26:19	0.8837600431340743	DAI	Coinbase	\N
477	2021-12-11 10:38:44	0.8792939687017373	DAI	Coinbase	\N
478	2021-12-12 12:11:06	0.8832277573166306	DAI	Coinbase	\N
479	2021-12-13 09:51:39	0.8832690405974714	DAI	Coinbase	\N
480	2021-12-14 13:39:13	0.885102251098176	DAI	Coinbase	\N
481	2021-12-15 08:43:54	0.8904874829717299	DAI	Coinbase	\N
482	2021-12-16 12:53:24	0.8853416075953984	DAI	Coinbase	\N
483	2021-12-17 11:40:17	0.8822189591167018	DAI	Coinbase	\N
484	2021-12-18 12:16:01	0.8899464143572868	DAI	Coinbase	\N
485	2021-12-19 16:36:29	0.8904896272548076	DAI	Coinbase	\N
486	2021-12-21 01:26:57	0.8902241307027934	DAI	Coinbase	\N
487	2021-12-21 21:58:41	0.8902241307027934	DAI	Coinbase	\N
488	2021-12-23 00:20:39	0.8842754960030553	DAI	Coinbase	\N
489	2021-12-23 11:53:48	0.8842754960030553	DAI	Coinbase	\N
490	2021-12-24 18:26:54	0.886588110789026	DAI	Coinbase	\N
491	2021-12-25 10:19:56	0.884558850827856	DAI	Coinbase	\N
492	2021-12-26 09:39:55	0.8826024534844141	DAI	Coinbase	\N
493	2021-12-28 00:06:41	0.881523135391506	DAI	Coinbase	\N
494	2021-12-28 16:17:23	0.881523135391506	DAI	Coinbase	\N
495	2021-12-29 14:35:32	0.8837720058586734	DAI	Coinbase	\N
496	2021-12-30 11:48:14	0.8832907668230525	DAI	Coinbase	\N
497	2021-12-31 13:18:28	0.8860864837934388	DAI	Coinbase	\N
498	2021-11-29 08:42:05	0.05572134834784326	AMP	Coinbase	\N
499	2021-11-29 08:42:24	0.05572134834784326	AMP	Coinbase	\N
500	2021-11-29 08:43:05	0.05572134834784326	AMP	Coinbase	\N
501	2021-11-29 08:44:08	0.86636	GRT	Coinbase	EUR
502	2021-11-29 08:44:41	0.866448	GRT	Coinbase	EUR
503	2021-11-29 08:45:23	0.866448	GRT	Coinbase	EUR
504	2021-11-29 08:45:42	0.866624	GRT	Coinbase	EUR
505	2021-01-19 17:32:31.099	30853.499999999996	BTC	Coinbase Pro	EUR
506	2021-01-19 17:32:31.099	30853.499999999996	BTC	Coinbase Pro	EUR
507	2021-01-19 17:32:31.099	30858.796349999997	BTC	Coinbase Pro	EUR
508	2021-01-19 17:32:31.099	30858.816450000002	BTC	Coinbase Pro	EUR
509	2021-01-19 17:32:31.099	30859.68075	BTC	Coinbase Pro	EUR
510	2021-01-19 17:32:31.099	30859.690799999997	BTC	Coinbase Pro	EUR
511	2021-01-19 17:32:31.099	30862.55505	BTC	Coinbase Pro	EUR
512	2021-02-05 08:00:55	-1400.9562651175938	ETH	Coinbase	EUR
513	2021-02-05 08:00:55	-1400.9562651175938	ETH	Coinbase	EUR
514	2021-02-05 08:00:55	-1400.9562651175938	ETH	Coinbase	EUR
515	2021-02-05 08:00:55	1495.7448458235299	EUR	Coinbase	ETH
516	2021-02-05 08:00:55	-1400.9562651175938	ETH	Coinbase	EUR
517	2021-08-09 08:02:03	0.853774	DAI	Coinbase	EUR
518	2021-08-10 10:11:44	0.855394	DAI	Coinbase	EUR
519	2021-08-11 13:49:50	0.853622	DAI	Coinbase	EUR
520	2021-08-12 14:19:44	0.851026	DAI	Coinbase	EUR
521	2021-08-13 14:03:45	0.852222	DAI	Coinbase	EUR
522	2021-08-14 10:17:08	0.84841	DAI	Coinbase	EUR
523	2021-08-15 10:04:02	0.847707	DAI	Coinbase	EUR
524	2021-08-16 09:27:51	0.848757	DAI	Coinbase	EUR
525	2021-08-17 08:19:32	0.849926	DAI	Coinbase	EUR
526	2021-08-18 13:07:15	0.855815	DAI	Coinbase	EUR
527	2021-08-19 12:18:26	0.856668	DAI	Coinbase	EUR
528	2021-08-20 09:56:11	0.864509	DAI	Coinbase	EUR
529	2021-08-21 15:26:25	0.854394	DAI	Coinbase	EUR
530	2021-08-23 03:46:08	0.860596	DAI	Coinbase	EUR
531	2021-08-23 14:18:02	0.860596	DAI	Coinbase	EUR
532	2021-08-24 09:15:01	0.85169	DAI	Coinbase	EUR
533	2021-08-25 14:08:05	0.852577	DAI	Coinbase	EUR
534	2021-08-26 09:15:48	0.849429	DAI	Coinbase	EUR
535	2021-08-27 11:25:51	0.850426	DAI	Coinbase	EUR
536	2021-08-28 14:04:33	0.850056	DAI	Coinbase	EUR
537	2021-08-29 08:41:49	0.847921	DAI	Coinbase	EUR
538	2021-08-30 13:54:09	0.847515	DAI	Coinbase	EUR
539	2021-08-31 15:14:48	0.845808	DAI	Coinbase	EUR
540	2021-09-01 12:44:10	0.850103	DAI	Coinbase	EUR
541	2021-09-02 08:46:33	0.844635	DAI	Coinbase	EUR
542	2021-09-03 12:32:35	0.842019	DAI	Coinbase	EUR
543	2021-09-04 10:25:41	0.840891	DAI	Coinbase	EUR
544	2021-09-05 11:12:54	0.840099	DAI	Coinbase	EUR
545	2021-09-06 12:35:54	0.841467	DAI	Coinbase	EUR
546	2021-09-07 09:48:41	0.842759	DAI	Coinbase	EUR
547	2021-09-08 10:49:02	0.845654	DAI	Coinbase	EUR
548	2021-09-09 07:58:39	0.849501	DAI	Coinbase	EUR
549	2021-09-10 10:45:17	0.849547	DAI	Coinbase	EUR
550	2021-09-11 07:55:34	0.84465	DAI	Coinbase	EUR
551	2021-09-12 09:29:25	0.848601	DAI	Coinbase	EUR
552	2021-09-13 08:31:24	0.855842	DAI	Coinbase	EUR
553	2021-09-14 12:38:44	0.850176	DAI	Coinbase	EUR
554	2021-09-15 08:55:09	0.848736	DAI	Coinbase	EUR
555	2021-09-16 08:37:24	0.847672	DAI	Coinbase	EUR
556	2021-09-17 09:25:56	0.851765	DAI	Coinbase	EUR
557	2021-09-18 11:40:11	0.856587	DAI	Coinbase	EUR
558	2021-09-19 12:47:19	0.856086	DAI	Coinbase	EUR
559	2021-09-21 13:32:30	0.845283	DAI	Coinbase	EUR
560	2021-09-22 11:55:11	0.848898	DAI	Coinbase	EUR
561	2021-09-23 11:18:56	0.856057	DAI	Coinbase	EUR
562	2021-09-24 13:47:47	0.853545	DAI	Coinbase	EUR
563	2021-09-25 00:47:52	0.855132	DAI	Coinbase	EUR
564	2021-09-25 13:08:49	0.855132	DAI	Coinbase	EUR
565	2021-09-26 12:30:45	0.856814	DAI	Coinbase	EUR
566	2021-09-27 12:26:31	0.856545	DAI	Coinbase	EUR
567	2021-09-28 14:18:14	0.850176	DAI	Coinbase	EUR
568	2021-09-29 10:32:58	0.851268	DAI	Coinbase	EUR
569	2021-09-30 14:19:47	0.865246	DAI	Coinbase	EUR
570	2021-10-01 11:30:07	0.868966	DAI	Coinbase	EUR
571	2021-10-02 11:01:32	0.864799	DAI	Coinbase	EUR
572	2021-10-03 08:59:23	0.862042	DAI	Coinbase	EUR
573	2021-10-04 10:03:45	0.864557	DAI	Coinbase	EUR
574	2021-10-05 11:37:57	0.863501	DAI	Coinbase	EUR
575	2021-10-06 09:00:11	0.86392	DAI	Coinbase	EUR
576	2021-10-07 09:01:35	0.867465	DAI	Coinbase	EUR
577	2021-10-08 08:28:59	0.866143	DAI	Coinbase	EUR
578	2021-10-09 08:06:41	0.865969	DAI	Coinbase	EUR
579	2021-10-10 12:45:36	0.866868	DAI	Coinbase	EUR
580	2021-10-11 15:20:57	0.865798	DAI	Coinbase	EUR
581	2021-10-12 14:00:43	0.871841	DAI	Coinbase	EUR
582	2021-10-13 08:48:00	0.868975	DAI	Coinbase	EUR
583	2021-10-14 13:50:04	0.866334	DAI	Coinbase	EUR
584	2021-10-15 10:05:54	0.86443	DAI	Coinbase	EUR
585	2021-10-16 10:15:24	0.866307	DAI	Coinbase	EUR
586	2021-10-17 11:32:14	0.864549	DAI	Coinbase	EUR
587	2021-10-18 09:12:30	0.866134	DAI	Coinbase	EUR
588	2021-10-19 08:43:23	0.861723	DAI	Coinbase	EUR
589	2021-10-20 12:13:53	0.859622	DAI	Coinbase	EUR
590	2021-10-21 08:23:12	0.858185	DAI	Coinbase	EUR
591	2021-10-22 10:27:29	0.860977	DAI	Coinbase	EUR
592	2021-10-23 08:24:18	0.863659	DAI	Coinbase	EUR
593	2021-10-24 14:31:04	0.864502	DAI	Coinbase	EUR
594	2021-10-25 12:20:53	0.863561	DAI	Coinbase	EUR
595	2021-10-26 08:36:09	0.865578	DAI	Coinbase	EUR
596	2021-10-27 08:20:13	0.86239	DAI	Coinbase	EUR
597	2021-10-28 10:13:49	0.861384	DAI	Coinbase	EUR
598	2021-10-29 12:32:35	0.855005	DAI	Coinbase	EUR
599	2021-10-30 12:46:04	0.864754	DAI	Coinbase	EUR
600	2021-10-31 15:47:51	0.868434	DAI	Coinbase	EUR
601	2021-11-01 12:07:38	0.865202	DAI	Coinbase	EUR
602	2021-11-02 11:52:40	0.863421	DAI	Coinbase	EUR
603	2021-11-03 11:21:25	0.865971	DAI	Coinbase	EUR
604	2021-11-04 13:42:19	0.8618	DAI	Coinbase	EUR
605	2021-11-05 09:50:19	0.86951	DAI	Coinbase	EUR
606	2021-11-06 08:36:22	0.868682	DAI	Coinbase	EUR
607	2021-11-07 10:31:09	0.86852	DAI	Coinbase	EUR
608	2021-11-08 14:30:00	0.863556	DAI	Coinbase	EUR
609	2021-11-09 14:59:40	0.86388	DAI	Coinbase	EUR
610	2021-11-10 14:37:38	0.863513	DAI	Coinbase	EUR
611	2021-11-11 11:13:04	0.874349	DAI	Coinbase	EUR
612	2021-11-12 10:59:39	0.874577	DAI	Coinbase	EUR
613	2021-11-13 14:34:16	0.880509	DAI	Coinbase	EUR
614	2021-11-14 08:30:45	0.876605	DAI	Coinbase	EUR
615	2021-11-15 09:29:55	0.881302	DAI	Coinbase	EUR
616	2021-11-16 12:14:01	0.882693	DAI	Coinbase	EUR
617	2021-11-17 14:08:38	0.885643	DAI	Coinbase	EUR
618	2021-11-18 09:28:08	0.890327	DAI	Coinbase	EUR
619	2021-11-19 12:52:41	0.878918	DAI	Coinbase	EUR
620	2021-11-20 14:10:31	0.891519	DAI	Coinbase	EUR
621	2021-11-21 12:14:45	0.891898	DAI	Coinbase	EUR
622	2021-11-22 12:11:08	0.89076	DAI	Coinbase	EUR
623	2021-11-23 11:52:59	0.889265	DAI	Coinbase	EUR
624	2021-11-24 13:16:02	0.890182	DAI	Coinbase	EUR
625	2021-11-25 11:56:57	0.894503	DAI	Coinbase	EUR
626	2021-11-26 11:57:29	0.89037	DAI	Coinbase	EUR
627	2021-11-27 10:12:28	0.883544	DAI	Coinbase	EUR
628	2021-11-29 08:59:47	0.887266	DAI	Coinbase	EUR
629	2021-11-30 14:18:31	0.88425	DAI	Coinbase	EUR
630	2021-12-01 08:32:47	0.880699	DAI	Coinbase	EUR
631	2021-12-02 09:00:29	0.884627	DAI	Coinbase	EUR
632	2021-12-03 15:09:37	0.882193	DAI	Coinbase	EUR
633	2021-02-05 08:00:55	-1400.9562651175938	ETH	Coinbase	EUR
634	2021-08-09 08:02:03	0.8537743138646571	DAI	Coinbase	\N
635	2021-08-10 10:11:44	0.8553943272988543	DAI	Coinbase	\N
636	2021-08-11 13:49:50	0.8536221011356178	DAI	Coinbase	\N
637	2021-08-12 14:19:44	0.8510264215759197	DAI	Coinbase	\N
638	2021-08-13 14:03:45	0.8522224187877911	DAI	Coinbase	\N
639	2021-08-14 10:17:08	0.8484095846108912	DAI	Coinbase	\N
640	2021-08-15 10:04:02	0.847707214175866	DAI	Coinbase	\N
641	2021-08-16 09:27:51	0.8487571817628591	DAI	Coinbase	\N
642	2021-08-17 08:19:32	0.8499255331484287	DAI	Coinbase	\N
643	2021-08-18 13:07:15	0.8558150012079034	DAI	Coinbase	\N
644	2021-08-19 12:18:26	0.856667621307741	DAI	Coinbase	\N
645	2021-08-20 09:56:11	0.86450879133493	DAI	Coinbase	\N
646	2021-08-21 15:26:25	0.8543939360704937	DAI	Coinbase	\N
647	2021-08-23 03:46:08	0.8605958712528334	DAI	Coinbase	\N
648	2021-08-23 14:18:02	0.8605958712528334	DAI	Coinbase	\N
649	2021-08-24 09:15:01	0.8516904395642656	DAI	Coinbase	\N
650	2021-08-25 14:08:05	0.8525767129664183	DAI	Coinbase	\N
651	2021-08-26 09:15:48	0.8494285225673855	DAI	Coinbase	\N
652	2021-08-27 11:25:51	0.8504261495237487	DAI	Coinbase	\N
653	2021-08-28 14:04:33	0.8500562165161353	DAI	Coinbase	\N
654	2021-08-29 08:41:49	0.8479207216442077	DAI	Coinbase	\N
655	2021-08-30 13:54:09	0.8475146618109123	DAI	Coinbase	\N
656	2021-08-31 15:14:48	0.8458077790293868	DAI	Coinbase	\N
657	2021-09-01 12:44:10	0.8501025295246286	DAI	Coinbase	\N
658	2021-09-02 08:46:33	0.8446353256073021	DAI	Coinbase	\N
659	2021-09-03 12:32:35	0.8420187141765069	DAI	Coinbase	\N
660	2021-09-04 10:25:41	0.8408907280625164	DAI	Coinbase	\N
661	2021-09-05 11:12:54	0.8400987737230561	DAI	Coinbase	\N
662	2021-09-06 12:35:54	0.841467326812695	DAI	Coinbase	\N
663	2021-09-07 09:48:41	0.8427594533579075	DAI	Coinbase	\N
664	2021-09-08 10:49:02	0.8456542439937145	DAI	Coinbase	\N
665	2021-09-09 07:58:39	0.8495011594557913	DAI	Coinbase	\N
666	2021-09-10 10:45:17	0.8495472141694329	DAI	Coinbase	\N
667	2021-09-11 07:55:34	0.8446501165919018	DAI	Coinbase	\N
668	2021-09-12 09:29:25	0.8486013711510472	DAI	Coinbase	\N
669	2021-09-13 08:31:24	0.8558421035485726	DAI	Coinbase	\N
670	2021-09-14 12:38:44	0.8501762475727523	DAI	Coinbase	\N
671	2021-09-15 08:55:09	0.8487359237571037	DAI	Coinbase	\N
672	2021-09-16 08:37:24	0.8476722285850521	DAI	Coinbase	\N
673	2021-09-17 09:25:56	0.8517646985846651	DAI	Coinbase	\N
674	2021-09-18 11:40:11	0.8565865564733675	DAI	Coinbase	\N
675	2021-09-19 12:47:19	0.8560860584128444	DAI	Coinbase	\N
676	2021-09-21 13:32:30	0.845283043840686	DAI	Coinbase	\N
677	2021-09-22 11:55:11	0.8488981674309838	DAI	Coinbase	\N
678	2021-09-23 11:18:56	0.8560571355086839	DAI	Coinbase	\N
679	2021-09-24 13:47:47	0.8535451537123152	DAI	Coinbase	\N
680	2021-09-25 00:47:52	0.8551315381659131	DAI	Coinbase	\N
681	2021-09-25 13:08:49	0.8551315381659131	DAI	Coinbase	\N
682	2021-09-26 12:30:45	0.8568143935859582	DAI	Coinbase	\N
683	2021-09-27 12:26:31	0.8565453726586224	DAI	Coinbase	\N
684	2021-09-28 14:18:14	0.8501756442768486	DAI	Coinbase	\N
685	2021-09-29 10:32:58	0.8512684389371903	DAI	Coinbase	\N
686	2021-09-30 14:19:47	0.8652460829565649	DAI	Coinbase	\N
687	2021-10-01 11:30:07	0.8689657687864485	DAI	Coinbase	\N
688	2021-10-02 11:01:32	0.8647991590656668	DAI	Coinbase	\N
689	2021-10-03 08:59:23	0.8620424983534131	DAI	Coinbase	\N
690	2021-10-04 10:03:45	0.8645567792283257	DAI	Coinbase	\N
691	2021-10-05 11:37:57	0.8635007459823668	DAI	Coinbase	\N
692	2021-10-06 09:00:11	0.8639201334442408	DAI	Coinbase	\N
693	2021-10-07 09:01:35	0.8674650126991609	DAI	Coinbase	\N
694	2021-10-08 08:28:59	0.866143185879763	DAI	Coinbase	\N
695	2021-10-09 08:06:41	0.865969052324413	DAI	Coinbase	\N
696	2021-10-10 12:45:36	0.8668684818007671	DAI	Coinbase	\N
697	2021-10-11 15:20:57	0.8657981282557599	DAI	Coinbase	\N
698	2021-10-12 14:00:43	0.8718413339013822	DAI	Coinbase	\N
699	2021-10-13 08:48:00	0.8689752497481814	DAI	Coinbase	\N
700	2021-10-14 13:50:04	0.866334266378689	DAI	Coinbase	\N
701	2021-10-15 10:05:54	0.8644302028580123	DAI	Coinbase	\N
702	2021-10-16 10:15:24	0.8663071558029151	DAI	Coinbase	\N
703	2021-10-17 11:32:14	0.8645486721068381	DAI	Coinbase	\N
704	2021-10-18 09:12:30	0.8661338852047775	DAI	Coinbase	\N
705	2021-10-19 08:43:23	0.8617229685477397	DAI	Coinbase	\N
706	2021-10-20 12:13:53	0.8596223794175211	DAI	Coinbase	\N
707	2021-10-21 08:23:12	0.8581854098913411	DAI	Coinbase	\N
708	2021-10-22 10:27:29	0.8609768574342349	DAI	Coinbase	\N
709	2021-10-23 08:24:18	0.8636593578379458	DAI	Coinbase	\N
710	2021-10-24 14:31:04	0.8645021139228569	DAI	Coinbase	\N
711	2021-10-25 12:20:53	0.8635607067619318	DAI	Coinbase	\N
712	2021-10-26 08:36:09	0.86557817181888	DAI	Coinbase	\N
713	2021-10-27 08:20:13	0.8623902996668912	DAI	Coinbase	\N
714	2021-10-28 10:13:49	0.8613836775921345	DAI	Coinbase	\N
715	2021-10-29 12:32:35	0.8550047220004316	DAI	Coinbase	\N
716	2021-10-30 12:46:04	0.8647540613817646	DAI	Coinbase	\N
717	2021-10-31 15:47:51	0.8684340119668359	DAI	Coinbase	\N
718	2021-11-01 12:07:38	0.865201740941255	DAI	Coinbase	\N
719	2021-11-02 11:52:40	0.8634207393969896	DAI	Coinbase	\N
720	2021-11-03 11:21:25	0.8659709339325251	DAI	Coinbase	\N
721	2021-11-04 13:42:19	0.861800318173923	DAI	Coinbase	\N
722	2021-11-05 09:50:19	0.8695101972624781	DAI	Coinbase	\N
723	2021-11-06 08:36:22	0.868682470809788	DAI	Coinbase	\N
724	2021-11-07 10:31:09	0.868520195216541	DAI	Coinbase	\N
725	2021-11-08 14:30:00	0.8635563020663123	DAI	Coinbase	\N
726	2021-11-09 14:59:40	0.8638797076282314	DAI	Coinbase	\N
727	2021-11-10 14:37:38	0.8635128795296849	DAI	Coinbase	\N
728	2021-11-11 11:13:04	0.8743487993499338	DAI	Coinbase	\N
729	2021-11-12 10:59:39	0.8745765348620057	DAI	Coinbase	\N
730	2021-11-13 14:34:16	0.8805091956126558	DAI	Coinbase	\N
731	2021-11-14 08:30:45	0.8766045116617114	DAI	Coinbase	\N
732	2021-11-15 09:29:55	0.8813023934492158	DAI	Coinbase	\N
733	2021-11-16 12:14:01	0.8826925615207089	DAI	Coinbase	\N
734	2021-11-17 14:08:38	0.8856432904144201	DAI	Coinbase	\N
735	2021-11-18 09:28:08	0.8903273632529202	DAI	Coinbase	\N
736	2021-11-19 12:52:41	0.8789179048940937	DAI	Coinbase	\N
737	2021-11-20 14:10:31	0.8915193550602837	DAI	Coinbase	\N
738	2021-11-21 12:14:45	0.8918976940330068	DAI	Coinbase	\N
739	2021-11-22 12:11:08	0.8907598775045942	DAI	Coinbase	\N
740	2021-11-23 11:52:59	0.8892647325284182	DAI	Coinbase	\N
741	2021-11-24 13:16:02	0.8901818833138452	DAI	Coinbase	\N
742	2021-11-25 11:56:57	0.8945026350907715	DAI	Coinbase	\N
743	2021-11-26 11:57:29	0.8903704587469331	DAI	Coinbase	\N
744	2021-11-27 10:12:28	0.8835440571098934	DAI	Coinbase	\N
745	2021-11-28 08:42:04	0.8823307527007179	DAI	Coinbase	\N
746	2021-11-29 08:59:47	0.8872658133574494	DAI	Coinbase	\N
747	2021-11-30 14:18:31	0.8842498957736418	DAI	Coinbase	\N
748	2021-12-01 08:32:47	0.880698523972643	DAI	Coinbase	\N
749	2021-12-02 09:00:29	0.8846267175717629	DAI	Coinbase	\N
750	2021-12-03 15:09:37	0.882193405258047	DAI	Coinbase	\N
751	2021-12-04 08:40:23	0.8835601838406949	DAI	Coinbase	\N
752	2021-12-05 12:52:48	0.8835010038917137	DAI	Coinbase	\N
753	2021-12-06 12:04:06	0.8850520459571081	DAI	Coinbase	\N
754	2021-12-07 09:53:54	0.8871614008474018	DAI	Coinbase	\N
755	2021-12-08 13:03:32	0.8896939377798074	DAI	Coinbase	\N
756	2021-12-09 09:41:38	0.8826480446342552	DAI	Coinbase	\N
757	2021-12-10 13:26:19	0.8837600431340743	DAI	Coinbase	\N
758	2021-12-11 10:38:44	0.8792939687017373	DAI	Coinbase	\N
759	2021-12-12 12:11:06	0.8832277573166306	DAI	Coinbase	\N
760	2021-12-13 09:51:39	0.8832690405974714	DAI	Coinbase	\N
761	2021-12-14 13:39:13	0.885102251098176	DAI	Coinbase	\N
762	2021-12-15 08:43:54	0.8904874829717299	DAI	Coinbase	\N
763	2021-12-16 12:53:24	0.8853416075953984	DAI	Coinbase	\N
764	2021-12-17 11:40:17	0.8822189591167018	DAI	Coinbase	\N
765	2021-12-18 12:16:01	0.8899464143572868	DAI	Coinbase	\N
766	2021-12-19 16:36:29	0.8904896272548076	DAI	Coinbase	\N
767	2021-12-21 01:26:57	0.8902241307027934	DAI	Coinbase	\N
768	2021-12-21 21:58:41	0.8902241307027934	DAI	Coinbase	\N
769	2021-12-23 00:20:39	0.8842754960030553	DAI	Coinbase	\N
770	2021-12-23 11:53:48	0.8842754960030553	DAI	Coinbase	\N
771	2021-12-24 18:26:54	0.886588110789026	DAI	Coinbase	\N
772	2021-12-25 10:19:56	0.884558850827856	DAI	Coinbase	\N
773	2021-12-26 09:39:55	0.8826024534844141	DAI	Coinbase	\N
774	2021-12-28 00:06:41	0.881523135391506	DAI	Coinbase	\N
775	2021-12-28 16:17:23	0.881523135391506	DAI	Coinbase	\N
776	2021-12-29 14:35:32	0.8837720058586734	DAI	Coinbase	\N
777	2021-12-30 11:48:14	0.8832907668230525	DAI	Coinbase	\N
778	2021-12-31 13:18:28	0.8860864837934388	DAI	Coinbase	\N
779	2021-11-29 08:42:05	0.05572134834784326	AMP	Coinbase	\N
780	2021-11-29 08:42:24	0.05572134834784326	AMP	Coinbase	\N
781	2021-11-29 08:43:05	0.05572134834784326	AMP	Coinbase	\N
782	2021-11-29 08:44:08	0.86636	GRT	Coinbase	EUR
783	2021-11-29 08:44:41	0.866448	GRT	Coinbase	EUR
784	2021-11-29 08:45:23	0.866448	GRT	Coinbase	EUR
785	2021-11-29 08:45:42	0.866624	GRT	Coinbase	EUR
786	2021-01-19 17:32:31.099	30853.499999999996	BTC	Coinbase Pro	EUR
787	2021-01-19 17:32:31.099	30853.499999999996	BTC	Coinbase Pro	EUR
788	2021-01-19 17:32:31.099	30858.796349999997	BTC	Coinbase Pro	EUR
789	2021-01-19 17:32:31.099	30858.816450000002	BTC	Coinbase Pro	EUR
790	2021-01-19 17:32:31.099	30859.68075	BTC	Coinbase Pro	EUR
791	2021-01-19 17:32:31.099	30859.690799999997	BTC	Coinbase Pro	EUR
792	2021-01-19 17:32:31.099	30862.55505	BTC	Coinbase Pro	EUR
793	2021-01-19 17:31:17	0.8282671126066964	USDT	Phemex Spot	\N
794	2021-01-19 17:31:17	30302.801411274904	BTC	Phemex Spot	\N
795	2021-01-03 06:40:23	26505.018067687164	BTC	Phemex Spot	\N
796	2021-01-03 06:40:23	0.8266344962686692	USDT	Phemex Spot	\N
797	2021-01-03 06:40:23	26505.018067687164	BTC	Phemex Spot	\N
798	2021-01-03 06:40:23	0.8266344962686692	USDT	Phemex Spot	\N
799	2021-01-02 23:03:39	24106.90173422772	BTC	Phemex Spot	\N
800	2021-01-02 23:03:39	0.8243202799355192	USDT	Phemex Spot	\N
801	2021-01-02 23:03:39	24106.90173422772	BTC	Phemex Spot	\N
802	2021-01-02 23:03:39	0.8243202799355192	USDT	Phemex Spot	\N
803	2021-01-19 17:31:17	0.8282671126066964	USDT	Phemex	\N
804	2021-01-19 17:31:17	30302.801411274904	BTC	Phemex	\N
805	2021-01-03 06:40:23	26505.018067687164	BTC	Phemex	\N
806	2021-01-03 06:40:23	0.8266344962686692	USDT	Phemex	\N
807	2021-01-03 06:40:23	26505.018067687164	BTC	Phemex	\N
808	2021-01-03 06:40:23	0.8266344962686692	USDT	Phemex	\N
809	2021-01-02 23:03:39	24106.90173422772	BTC	Phemex	\N
810	2021-01-02 23:03:39	0.8243202799355192	USDT	Phemex	\N
811	2021-01-02 23:03:39	24106.90173422772	BTC	Phemex	\N
812	2021-01-02 23:03:39	0.8243202799355192	USDT	Phemex	\N
813	2021-03-17 12:03:17	47746.846623517624	BTC	Phemex	\N
814	2021-01-04 20:54:56	26944.846107491692	BTC	Phemex	\N
815	2021-01-04 20:53:40	26944.846107491692	BTC	Phemex	\N
816	2021-04-09 02:36:37	48727.12069323635	BTC	Phemex	\N
817	2021-04-08 04:10:16	47281.06548137643	BTC	Phemex	\N
818	2021-01-04 20:53:40	26944.846107491692	BTC	Phemex	\N
819	2021-01-04 20:54:56	26944.846107491692	BTC	Phemex	\N
820	2021-03-17 12:03:17	47746.846623517624	BTC	Phemex	\N
821	2021-04-08 04:10:16	47281.06548137643	BTC	Phemex	\N
822	2021-04-09 02:36:37	48727.12069323635	BTC	Phemex	\N
823	2021-01-04 20:53:40	26944.85	BTC	Phemex	EUR
824	2021-01-04 20:54:56	26944.85	BTC	Phemex	EUR
825	2021-03-17 12:03:17	47746.85	BTC	Phemex	EUR
826	2021-04-08 04:10:16	47281.07	BTC	Phemex	EUR
827	2021-04-09 02:36:37	48727.12	BTC	Phemex	EUR
828	2021-01-04 20:53:40	26944.846107491692	BTC	Phemex	\N
829	2021-01-04 20:54:56	26944.846107491692	BTC	Phemex	\N
830	2021-03-17 12:03:17	47746.846623517624	BTC	Phemex	\N
831	2021-04-08 04:10:16	47281.06548137643	BTC	Phemex	\N
832	2021-04-09 02:36:37	48727.12069323635	BTC	Phemex	\N
833	2021-01-08 20:35:42	32228.81707793265	BTC	Phemex	\N
834	2021-01-08 20:35:42	0.7698147349749233	USD	Phemex	\N
835	2021-01-18 10:58:57	0.8089930953310657	USD	Phemex	\N
836	2021-01-18 10:58:57	29660.28800715679	BTC	Phemex	\N
837	2021-01-19 14:24:33	30302.801411274904	BTC	Phemex	\N
838	2021-01-19 14:24:33	0.8221709711007489	USD	Phemex	\N
839	2021-02-10 08:14:32	38434.83981506046	BTC	Phemex	\N
840	2021-02-10 08:14:32	0.8823007240220728	USD	Phemex	\N
841	2021-07-09 07:11:50	27801.439907762073	BTC	Phemex	\N
842	2021-08-04 16:34:23	32323.880257639797	BTC	Phemex	\N
843	2021-01-04 20:53:40	26944.846107491692	BTC	Phemex	\N
844	2021-01-04 20:54:56	26944.846107491692	BTC	Phemex	\N
845	2021-03-17 12:03:17	47746.846623517624	BTC	Phemex	\N
846	2021-04-08 04:10:16	47281.06548137643	BTC	Phemex	\N
847	2021-04-09 02:36:37	48727.12069323635	BTC	Phemex	\N
848	2021-07-09 07:11:50	0.842753	USD	Phemex	EUR
849	2021-07-09 07:11:50	27799.55	BTC	Phemex	EUR
850	2021-08-04 16:34:23	33345.8	BTC	Phemex	EUR
851	2021-01-08 20:35:42	32228.82	BTC	Phemex	EUR
852	2021-01-08 20:35:42	0.769815	USD	Phemex	EUR
853	2021-01-18 10:58:57	0.808993	USD	Phemex	EUR
854	2021-01-18 10:58:57	29660.29	BTC	Phemex	EUR
855	2021-01-19 14:24:33	30302.8	BTC	Phemex	EUR
856	2021-01-19 14:24:33	0.822171	USD	Phemex	EUR
857	2021-02-10 08:14:32	38434.84	BTC	Phemex	EUR
858	2021-02-10 08:14:32	0.82396	USD	Phemex	EUR
859	2021-07-09 07:11:50	27801.44	BTC	Phemex	EUR
860	2021-07-09 07:11:50	0.842753	USD	Phemex	EUR
861	2021-08-04 16:34:23	32323.88	BTC	Phemex	EUR
862	2021-08-04 16:34:23	0.844575	USD	Phemex	EUR
863	2021-01-04 20:53:40	26944.85	BTC	Phemex	EUR
864	2021-01-04 20:54:56	26944.85	BTC	Phemex	EUR
865	2021-03-17 12:03:17	47746.85	BTC	Phemex	EUR
866	2021-04-08 04:10:16	47281.07	BTC	Phemex	EUR
867	2021-04-09 02:36:37	48727.12	BTC	Phemex	EUR
\.


--
-- Data for Name: gain; Type: TABLE DATA; Schema: public; Owner: appuser
--

COPY public.gain (id, ammount, costbasis, in_date_time, out_date_time, proceeds, profit, short_long, buy_at_name, currency_ticker, sell_at_name) FROM stdin;
\.


--
-- Data for Name: hold; Type: TABLE DATA; Schema: public; Owner: appuser
--

COPY public.hold (id, ammount, date_time, factor, in_currency_ticker, location_name) FROM stdin;
\.


--
-- Data for Name: income; Type: TABLE DATA; Schema: public; Owner: appuser
--

COPY public.income (id, cost_base, cost_base_calculation, ammount, out_date_time, type, worth_at_out, in_date_time, info, worth_at_income, currency_ticker, out_at_name, in_at_name) FROM stdin;
\.


--
-- Data for Name: location; Type: TABLE DATA; Schema: public; Owner: appuser
--

COPY public.location (name, information, is_exchange) FROM stdin;
Binance	\N	t
ByBit	\N	t
Coinbase	\N	t
Coinbase Pro	\N	t
Cryptopia	\N	t
Home B Hardfork BTCP	\N	t
Home BTC Wallet	\N	t
Home BTCP	\N	t
Home Wallet ETH	\N	t
Home Wallet ETH 2	\N	t
Home Wallet LTC	\N	t
Home Z Hardfork BTCP	\N	t
NEON home Wallet	\N	t
Phemex	\N	t
Phemex Spot	\N	t
Phemex USD Correction	\N	t
SVD Home Wallet	\N	t
ico.savedroid.com	\N	t
\.


--
-- Data for Name: trades; Type: TABLE DATA; Schema: public; Owner: appuser
--

COPY public.trades (id, buy_fee, buy_value, buying_time, comment, sell_fee, sell_value, selling_time, status, stop_loss, trading_type, value, buy_currency_ticker, buy_fiat_exchange_id, currency_ticker, exchange_name, sell_currency_ticker, sell_fiat_exchange_id, trade_fiat_exchange_id) FROM stdin;
\.


--
-- Data for Name: trades_images; Type: TABLE DATA; Schema: public; Owner: appuser
--

COPY public.trades_images (trade_id, images_id) FROM stdin;
\.


--
-- Data for Name: trading_images; Type: TABLE DATA; Schema: public; Owner: appuser
--

COPY public.trading_images (id, address, image_time, name) FROM stdin;
\.


--
-- Data for Name: transaction; Type: TABLE DATA; Schema: public; Owner: appuser
--

COPY public.transaction (id, comment, date_time, fee, in_value, out_value, type, exchange_name, fee_currency_ticker, in_currency_ticker, in_fiat_exchange_id, out_currency_ticker, out_fiat_exchange_id, trade_id) FROM stdin;
1	\N	2018-01-15 11:55:50	7.34	2.48559454	500	Trade	Coinbase	EUR	LTC	1	EUR	\N	\N
2	\N	2018-01-15 11:56:33	7.34	0.44488819	500	Trade	Coinbase	EUR	ETH	2	EUR	\N	\N
3	\N	2018-01-15 12:01:15	\N	0.000727	\N	Gift	Coinbase	\N	BTC	3	\N	\N	\N
4	\N	2018-01-15 14:26:16	0.00105	\N	0.44488819	Withdraw	Coinbase	ETH	\N	\N	ETH	\N	\N
5	\N	2018-01-15 14:30:02	\N	0.44383819	\N	Deposit	Binance	\N	ETH	\N	\N	\N	\N
6	\N	2018-01-15 15:32:54	2.52	2517.48	0.22176	Trade	Binance	XVG	XVG	5	ETH	4	\N
7	\N	2018-01-15 15:37:17	0.177	176.823	0.22125	Trade	Binance	XRP	XRP	7	ETH	6	\N
8	\N	2018-01-16 12:13:50	14.68	1.07151841	1000	Trade	Coinbase	EUR	ETH	8	EUR	\N	\N
9	\N	2018-01-16 12:19:48	0.001092	\N	0.550552	Withdraw	Coinbase	ETH	\N	\N	ETH	\N	\N
10	\N	2018-01-16 12:21:26	\N	0.54946	\N	Deposit	Binance	\N	ETH	\N	\N	\N	\N
11	\N	2018-01-16 19:15:50	2.505	2502.495	0.2004	Trade	Binance	XVG	XVG	10	ETH	9	\N
12	\N	2018-01-16 20:00:20	0.371	370.629	0.2127685	Trade	Binance	ADA	ADA	12	ETH	11	\N
13	\N	2018-01-16 21:05:22	0.117	116.883	0.136656	Trade	Binance	XRP	XRP	14	ETH	13	\N
14	\N	2018-01-19 20:38:22	7.34	3.0628017	500	Trade	Coinbase	EUR	LTC	15	EUR	\N	\N
15	\N	2018-01-19 20:38:58	7.34	0.56533001	500	Trade	Coinbase	EUR	ETH	16	EUR	\N	\N
16	\N	2018-02-05 16:16:37	14.68	1.70861071	1000	Trade	Coinbase	EUR	ETH	17	EUR	\N	\N
17	\N	2018-02-07 18:49:52	\N	0.1	\N	Deposit	Home Wallet ETH 2	\N	ETH	\N	\N	\N	\N
18	\N	2018-02-07 18:49:49	0.000525	\N	0.100525	Withdraw	Coinbase	ETH	\N	\N	ETH	\N	\N
19	\N	2018-02-09 16:35:43	14.9	985.1	1.45521667	Trade	Coinbase	EUR	EUR	\N	ETH	18	\N
20	\N	2018-02-20 13:28:27	\N	5.54817024	\N	Deposit	Home Wallet LTC	\N	LTC	\N	\N	\N	\N
21	\N	2018-02-20 13:28:56	0.000226	\N	5.54839624	Withdraw	Coinbase	LTC	\N	\N	LTC	\N	\N
22	\N	2018-02-21 19:06:03	3.67	0.36315002	250	Trade	Coinbase	EUR	ETH	19	EUR	\N	\N
23	\N	2018-02-21 21:32:11	9.54	3.76397766	650	Trade	Coinbase	EUR	LTC	20	EUR	\N	\N
24	\N	2018-02-21 21:35:55	2.99	0.48484655	85.1	Trade	Coinbase	EUR	LTC	21	EUR	\N	\N
25	\N	2018-02-22 08:20:20	\N	4.24859821	\N	Deposit	Home Wallet LTC	\N	LTC	\N	\N	\N	\N
26	\N	2018-02-22 08:20:34	0.000226	\N	4.24882421	Withdraw	Coinbase	LTC	\N	\N	LTC	\N	\N
27	\N	2018-02-22 14:15:20	6.3e-05	\N	0.099937	Withdraw	Home Wallet ETH 2	ETH	\N	\N	ETH	\N	\N
28	\N	2018-02-22 14:15:21	\N	0.099937	\N	Deposit	Home Wallet ETH	\N	ETH	\N	\N	\N	\N
29	\N	2018-02-22 13:08:12	\N	1.60214748	\N	Deposit	Home Wallet ETH	\N	ETH	\N	\N	\N	\N
30	\N	2018-02-22 13:08:38	0.000168	\N	1.60231548	Withdraw	Coinbase	ETH	\N	\N	ETH	\N	\N
31	\N	2018-02-23 16:44:28	7.34	0.05842777	500	Trade	Coinbase	EUR	BTC	22	EUR	\N	\N
32	\N	2018-02-24 18:43:20	\N	0.05891411	\N	Deposit	Cryptopia	\N	BTC	\N	\N	\N	\N
33	\N	2018-02-24 18:43:43	0.00024066	\N	0.05915477	Withdraw	Coinbase	BTC	\N	\N	BTC	\N	\N
34	\N	2018-02-24 19:10:03	2.554e-05	0.9980039	0.012772	Trade	Cryptopia	BTC	ZCL	24	BTC	23	\N
35	\N	2018-02-24 19:14:03	6.69e-05	2.6337542	0.033449	Trade	Cryptopia	BTC	ZCL	26	BTC	25	\N
36	\N	2018-02-24 19:14:20	2.505e-05	0.9862458	0.012525	Trade	Cryptopia	BTC	ZCL	28	BTC	27	\N
37	\N	2018-02-27 10:59:44	7.34	0.05609421	500	Trade	Coinbase	EUR	BTC	29	EUR	\N	\N
38	\N	2018-02-27 22:50:20	\N	0.05606859	\N	Deposit	Home BTC Wallet	\N	BTC	\N	\N	\N	\N
39	\N	2018-02-27 22:50:26	2.562e-05	\N	0.05609421	Withdraw	Coinbase	BTC	\N	\N	BTC	\N	\N
40	\N	2018-03-03 13:02:06	\N	4.6180039	\N	Gift	Home Z Hardfork BTCP	\N	BTCP	30	\N	\N	\N
41	\N	2018-03-08 22:50:27	19.18	0.83171577	500	Trade	Coinbase	EUR	ETH	31	EUR	\N	\N
42	\N	2018-03-08 23:14:42	9.63	0.420023	251	Trade	Coinbase	EUR	ETH	32	EUR	\N	\N
43	\N	2018-03-12 08:42:08	0.00032669	0.32636245	293	Trade	Binance	ETH	ETH	34	XRP	33	\N
44	\N	2018-03-12 09:21:03	0.00262	2.61738	0.326665	Trade	Binance	NEO	NEO	36	ETH	35	\N
45	\N	2018-03-12 12:20:04	\N	\N	2	Withdraw	Binance	\N	\N	\N	NEO	\N	\N
46	\N	2018-03-12 12:20:04	\N	2	\N	Deposit	NEON home Wallet	\N	NEO	\N	\N	\N	\N
47	\N	2018-03-25 11:19:24	\N	99900	500	Trade	ico.savedroid.com	\N	SVD	37	EUR	\N	\N
48	\N	2018-03-25 11:20:00	\N	\N	99900	Withdraw	ico.savedroid.com	\N	\N	\N	SVD	\N	\N
49	\N	2018-03-25 11:20:00	\N	99900	\N	Deposit	SVD Home Wallet	\N	SVD	\N	\N	\N	\N
50	\N	2018-04-02 13:05:51	13.43	0.05828629	350	Trade	Coinbase	EUR	BTC	38	EUR	\N	\N
51	\N	2018-04-03 18:06:12	5.76	0.02372267	150	Trade	Coinbase	EUR	BTC	39	EUR	\N	\N
52	\N	2018-04-05 16:47:17	\N	0.00351907	\N	Income	Binance	\N	GAS	40	\N	\N	\N
53	\N	2018-04-06 09:19:45	\N	0.00877968	\N	Income	NEON home Wallet	\N	GAS	41	\N	\N	\N
54	\N	2018-04-15 06:09:47	0.00080839	0.8083648	5019	Trade	Binance	ETH	ETH	43	XVG	42	\N
55	\N	2018-04-15 11:03:00	2.1e-05	\N	0.0048	Donation	Home Wallet ETH	ETH	\N	\N	ETH	44	\N
56	\N	2018-04-16 06:19:42	8.04	531.33	0.08200896	Trade	Coinbase	EUR	EUR	\N	BTC	45	\N
57	\N	2018-04-23 21:12:13	\N	0.00993444	\N	Income	NEON home Wallet	\N	GAS	46	\N	\N	\N
58	\N	2018-05-03 09:38:54	\N	0.00555642	\N	Income	Binance	\N	GAS	47	\N	\N	\N
59	\N	2018-05-13 12:26:26	7.8	0.8887724	531.33	Trade	Coinbase	EUR	ETH	48	EUR	\N	\N
60	\N	2018-05-16 07:09:52	9.16	605.78	1.07025558	Trade	Coinbase	EUR	EUR	\N	ETH	49	\N
61	\N	2018-05-21 08:50:05	\N	0.05606859	\N	Gift	Home B Hardfork BTCP	\N	BTCP	50	\N	\N	\N
62	\N	2018-05-21 08:57:04	9.6e-06	\N	4.60800399	Withdraw	Home Z Hardfork BTCP	BTCP	\N	\N	BTCP	\N	\N
63	\N	2018-05-21 08:57:04	\N	4.60799439	\N	Deposit	Home B Hardfork BTCP	\N	BTCP	\N	\N	\N	\N
64	\N	2018-05-21 09:35:06	0.00017	\N	4.66406298	Withdraw	Home B Hardfork BTCP	BTCP	\N	\N	BTCP	\N	\N
65	\N	2018-05-21 09:35:06	\N	4.66389298	\N	Deposit	Home BTCP	\N	BTCP	\N	\N	\N	\N
66	\N	2018-05-27 17:00:18	4.4	0.60042328	300	Trade	Coinbase	EUR	ETH	51	EUR	\N	\N
67	\N	2018-05-28 22:36:17	4.49	3.0547284	305.78	Trade	Coinbase	EUR	LTC	52	EUR	\N	\N
68	\N	2018-06-02 03:47:01	\N	0.00574163	\N	Income	Binance	\N	GAS	53	\N	\N	\N
69	\N	2018-06-14 19:14:07	11.51	3.32855666	300	Trade	Coinbase	EUR	LTC	54	EUR	\N	\N
70	\N	2018-07-02 17:03:05	19.18	1.17261731	500	Trade	Coinbase	EUR	ETH	55	EUR	\N	\N
71	\N	2018-07-03 04:00:56	\N	0.00555642	\N	Income	Binance	\N	GAS	56	\N	\N	\N
72	\N	2018-07-24 09:31:52	0.00053458	0.02619458	0.706	Trade	Binance	BNB	BNB	58	XRP	57	\N
73	\N	2018-07-24 09:31:52	0.00035388	0.01733996	0.02037354	Trade	Binance	BNB	BNB	60	GAS	59	\N
74	\N	2018-07-24 09:31:53	\N	0.00184737	0.975	Trade	Binance	\N	BNB	62	XVG	61	\N
75	\N	2018-08-03 12:31:38	\N	0.00574163	\N	Income	Binance	\N	GAS	63	\N	\N	\N
76	\N	2018-08-07 11:58:01	29.13	2.73023712	1000	Trade	Coinbase	EUR	ETH	64	EUR	\N	\N
77	\N	2018-09-05 08:19:05	\N	0.00574163	\N	Income	Binance	\N	GAS	65	\N	\N	\N
78	\N	2018-09-14 10:55:00	\N	\N	5.5735333	Withdraw	Coinbase	\N	\N	\N	ETH	\N	\N
79	\N	2018-09-14 10:55:00	\N	5.5735333	\N	Deposit	Coinbase Pro	\N	ETH	\N	\N	\N	\N
80	\N	2018-09-14 11:06:00	\N	\N	6.38328506	Withdraw	Coinbase	\N	\N	\N	LTC	\N	\N
81	\N	2018-09-14 11:06:00	\N	6.38328506	\N	Deposit	Coinbase Pro	\N	LTC	\N	\N	\N	\N
82	\N	2018-09-14 21:50:07	0.01	\N	0.80852594	Withdraw	Binance	ETH	\N	\N	ETH	\N	\N
83	\N	2018-09-14 21:56:44	\N	0.79852594	\N	Deposit	Coinbase	\N	ETH	\N	\N	\N	\N
84	\N	2018-09-14 22:09:00	\N	\N	0.79852594	Withdraw	Coinbase	\N	\N	\N	ETH	\N	\N
85	\N	2018-09-14 22:09:00	\N	0.79852594	\N	Deposit	Coinbase Pro	\N	ETH	\N	\N	\N	\N
86	\N	2018-09-15 01:32:38	0.67689444	224.95458502	1.24589442	Trade	Coinbase Pro	EUR	EUR	\N	ETH	66	\N
87	\N	2018-09-15 01:32:38	2.3502974	781.08216905	4.32763888	Trade	Coinbase Pro	EUR	EUR	\N	ETH	67	\N
88	\N	2018-09-15 08:15:15	\N	9.79643045	\N	Deposit	Coinbase	\N	LTC	\N	\N	\N	\N
89	\N	2018-09-15 08:15:25	0.000338	\N	9.79676845	Withdraw	Home Wallet LTC	LTC	\N	\N	LTC	\N	\N
90	\N	2018-09-15 08:24:00	\N	\N	9.79643045	Withdraw	Coinbase	\N	\N	\N	LTC	\N	\N
91	\N	2018-09-15 08:24:00	\N	9.79643045	\N	Deposit	Coinbase Pro	\N	LTC	\N	\N	\N	\N
92	\N	2018-09-15 21:56:33	6.3e-05	\N	1.69720048	Withdraw	Home Wallet ETH	ETH	\N	\N	ETH	\N	\N
93	\N	2018-09-15 21:56:47	\N	1.69720048	\N	Deposit	Coinbase	\N	ETH	\N	\N	\N	\N
94	\N	2018-09-15 22:13:00	\N	\N	1.69720048	Withdraw	Coinbase	\N	\N	\N	ETH	\N	\N
95	\N	2018-09-15 22:13:00	\N	1.69720048	\N	Deposit	Coinbase Pro	\N	ETH	\N	\N	\N	\N
96	\N	2018-09-16 06:03:07	1.1304	375.6696	8	Trade	Coinbase Pro	EUR	EUR	\N	LTC	68	\N
97	\N	2018-09-16 23:08:02	\N	0.0560646	\N	Deposit	Coinbase	\N	BTC	\N	\N	\N	\N
98	\N	2018-09-16 23:08:10	3.99e-06	\N	0.05606859	Withdraw	Home BTC Wallet	BTC	\N	\N	BTC	\N	\N
99	\N	2018-09-16 23:36:10	1.41417842	469.97862779	2.49572642	Trade	Coinbase Pro	EUR	EUR	\N	ETH	69	\N
100	\N	2018-09-17 00:26:00	\N	\N	0.0560646	Withdraw	Coinbase	\N	\N	\N	BTC	\N	\N
101	\N	2018-09-17 00:26:00	\N	0.0560646	\N	Deposit	Coinbase Pro	\N	BTC	\N	\N	\N	\N
102	\N	2018-09-17 06:34:04	0.07365072	24.47658928	0.0044	Trade	Coinbase Pro	EUR	EUR	\N	BTC	70	\N
103	\N	2018-09-17 06:34:04	0.86480341	287.40299875	0.0516646	Trade	Coinbase Pro	EUR	EUR	\N	BTC	71	\N
104	\N	2018-09-17 10:06:51	0.53232566	176.90956042	3.69670596	Trade	Coinbase Pro	EUR	EUR	\N	LTC	72	\N
105	\N	2018-09-17 10:06:51	0.135	44.865	0.9375	Trade	Coinbase Pro	EUR	EUR	\N	LTC	73	\N
106	\N	2018-09-17 10:06:51	0.072	23.928	0.5	Trade	Coinbase Pro	EUR	EUR	\N	LTC	74	\N
107	\N	2018-09-17 10:06:51	0.0288	9.5712	0.2	Trade	Coinbase Pro	EUR	EUR	\N	LTC	75	\N
108	\N	2018-09-17 10:06:51	0.40975338	136.17470502	2.84550955	Trade	Coinbase Pro	EUR	EUR	\N	LTC	76	\N
109	\N	2018-10-03 20:51:17	\N	0.00555642	\N	Income	Binance	\N	GAS	77	\N	\N	\N
110	\N	2018-11-02 14:36:16	\N	0.00574163	\N	Income	Binance	\N	GAS	78	\N	\N	\N
111	\N	2018-11-16 19:56:34	2.54237288	22.76887767	849.99999976	Trade	Coinbase Pro	EUR	LTC	79	EUR	\N	\N
112	\N	2018-11-16 19:57:14	2.54237275	0.17485069	849.9999545	Trade	Coinbase Pro	EUR	BTC	80	EUR	\N	\N
113	\N	2018-11-18 11:33:42	3.81355932	34.52434656	1274.99999966	Trade	Coinbase Pro	EUR	LTC	81	EUR	\N	\N
114	\N	2018-11-19 14:18:52	5.57233899	1851.87399055	57.29322423	Trade	Coinbase Pro	EUR	EUR	\N	LTC	82	\N
115	\N	2018-11-19 16:06:32	3.73878355	0.27919163	1249.99996537	Trade	Coinbase Pro	EUR	BTC	83	EUR	\N	\N
116	\N	2018-11-19 16:37:41	0.0394131	0.40374	13.1771127	Trade	Coinbase Pro	EUR	LTC	84	EUR	\N	\N
117	\N	2018-11-19 16:37:41	0.2213057	2.26701189	73.9898726	Trade	Coinbase Pro	EUR	LTC	85	EUR	\N	\N
118	\N	2018-11-19 16:37:41	0.74843962	7.65510507	250.22831385	Trade	Coinbase Pro	EUR	LTC	86	EUR	\N	\N
119	\N	2018-11-19 16:37:41	0.469584	4.8	156.997584	Trade	Coinbase Pro	EUR	LTC	87	EUR	\N	\N
120	\N	2018-11-19 16:37:41	0.09792	1	32.73792	Trade	Coinbase Pro	EUR	LTC	88	EUR	\N	\N
121	\N	2018-11-19 16:37:41	3.49451713	35.68747071	1168.33356111	Trade	Coinbase Pro	EUR	LTC	89	EUR	\N	\N
122	\N	2018-11-19 16:37:41	1.45787329	14.86109365	487.41563564	Trade	Coinbase Pro	EUR	LTC	90	EUR	\N	\N
123	\N	2018-11-23 10:34:46	2.24327019	6.86330178	749.99999912	Trade	Coinbase Pro	EUR	ETH	91	EUR	\N	\N
124	\N	2018-11-23 10:34:55	0.66970901	7.78369371	223.90604461	Trade	Coinbase Pro	EUR	LTC	92	EUR	\N	\N
125	\N	2018-11-23 10:34:55	0.23320811	2.71046158	77.96924623	Trade	Coinbase Pro	EUR	LTC	93	EUR	\N	\N
126	\N	2018-11-23 10:34:55	0.08604	1	28.76604	Trade	Coinbase Pro	EUR	LTC	94	EUR	\N	\N
127	\N	2018-11-23 10:34:55	1.25431307	14.57825509	419.35866905	Trade	Coinbase Pro	EUR	LTC	95	EUR	\N	\N
128	\N	2018-12-03 09:05:46	\N	0.00555642	\N	Income	Binance	\N	GAS	96	\N	\N	\N
129	\N	2019-01-02 13:38:38	\N	0.00574163	\N	Income	Binance	\N	GAS	97	\N	\N	\N
130	\N	2019-01-04 13:50:28	\N	3.125	\N	Gift	Coinbase	\N	ZRX	98	\N	\N	\N
131	\N	2019-01-04 13:52:14	\N	3.125	\N	Gift	Coinbase	\N	ZRX	99	\N	\N	\N
132	\N	2019-01-04 14:04:08	\N	3.125	\N	Gift	Coinbase	\N	ZRX	100	\N	\N	\N
133	\N	2019-01-10 13:37:09	\N	\N	6.86330178	Withdraw	Coinbase	ETH	\N	\N	ETH	\N	\N
134	\N	2019-01-10 13:37:09	\N	6.86309178	\N	Deposit	Home Wallet ETH	\N	ETH	\N	\N	\N	\N
135	\N	2019-01-10 13:38:02	\N	1.69717948	\N	Deposit	Coinbase	\N	ETH	\N	\N	\N	\N
136	\N	2019-01-10 13:37:26	8.4e-05	\N	1.69717948	Withdraw	Home Wallet ETH	ETH	\N	\N	ETH	\N	\N
137	\N	2019-01-10 13:37:26	\N	1.69717948	\N	Deposit	Coinbase	\N	ETH	\N	\N	\N	\N
138	\N	2019-01-10 22:35:50	\N	\N	1.69717948	Withdraw	Coinbase	\N	\N	\N	ETH	\N	\N
139	\N	2019-01-10 22:35:50	\N	1.69703248	\N	Deposit	Home Wallet ETH	\N	ETH	\N	\N	\N	\N
140	\N	2019-01-10 22:35:20	\N	\N	1.69717948	Withdraw	Coinbase	\N	\N	\N	ETH	\N	\N
141	\N	2019-01-18 13:11:40	4.2e-05	\N	6.86317578	Withdraw	Home Wallet ETH	ETH	\N	\N	ETH	\N	\N
142	\N	2019-01-18 13:11:40	\N	6.86317578	\N	Deposit	Coinbase	\N	ETH	\N	\N	\N	\N
143	\N	2019-02-01 15:12:45	\N	0.00497608	\N	Income	Binance	\N	GAS	101	\N	\N	\N
144	\N	2019-02-09 16:40:20	1.03061716	342.5084364	8.9486599	Trade	Coinbase Pro	EUR	EUR	\N	LTC	102	\N
145	\N	2019-02-09 16:40:20	1.84272	612.39728	16	Trade	Coinbase Pro	EUR	EUR	\N	LTC	103	\N
146	\N	2019-02-09 16:40:20	0.11122911	36.96513969	0.96603358	Trade	Coinbase Pro	EUR	EUR	\N	LTC	104	\N
147	\N	2019-02-09 16:40:20	0.11519952	38.28463911	1.0005169	Trade	Coinbase Pro	EUR	EUR	\N	LTC	105	\N
148	\N	2019-02-09 16:40:20	0.092856	30.85914405	0.8064617	Trade	Coinbase Pro	EUR	EUR	\N	LTC	106	\N
149	\N	2019-02-09 16:40:20	3.80152612	1263.3738485	33.02515962	Trade	Coinbase Pro	EUR	EUR	\N	LTC	107	\N
150	\N	2019-02-09 16:41:15	0.68565736	227.86679611	5.96431246	Trade	Coinbase Pro	EUR	EUR	\N	LTC	108	\N
151	\N	2019-02-09 16:41:15	1.81999949	604.84649598	15.83158913	Trade	Coinbase Pro	EUR	EUR	\N	LTC	109	\N
152	\N	2019-02-09 16:41:15	0.62234595	206.82630405	5.415	Trade	Coinbase Pro	EUR	EUR	\N	LTC	110	\N
153	\N	2019-02-09 16:41:15	0.28131161	93.48922644	2.44767784	Trade	Coinbase Pro	EUR	EUR	\N	LTC	111	\N
154	\N	2019-02-09 16:41:15	0.26902922	89.40737861	2.34142057	Trade	Coinbase Pro	EUR	EUR	\N	LTC	112	\N
155	\N	2019-02-12 09:10:14	\N	0.16511154	\N	Income	NEON home Wallet	\N	GAS	113	\N	\N	\N
156	\N	2019-02-21 08:49:39	\N	888.78126351	6.86317578	Trade	Coinbase Pro	\N	EUR	\N	ETH	114	\N
157	\N	2019-02-22 10:47:33	\N	0.01851852	\N	Gift	Coinbase	\N	ZEC	115	\N	\N	\N
158	\N	2019-02-22 10:48:04	\N	0.01851852	\N	Gift	Coinbase	\N	ZEC	116	\N	\N	\N
159	\N	2019-02-22 10:50:25	\N	0.01851852	\N	Gift	Coinbase	\N	ZEC	117	\N	\N	\N
160	\N	2019-03-04 05:33:46	\N	0.00518599	\N	Income	Binance	\N	GAS	118	\N	\N	\N
161	\N	2019-04-03 05:57:20	\N	0.00502392	\N	Income	Binance	\N	GAS	119	\N	\N	\N
162	\N	2019-04-09 13:10:32	2.44106149	1624.93326807	0.35118134	Trade	Coinbase Pro	EUR	EUR	\N	BTC	120	\N
163	\N	2019-04-09 13:10:41	0.28713219	191.13432517	0.04130804	Trade	Coinbase Pro	EUR	EUR	\N	BTC	121	\N
164	\N	2019-04-09 13:10:44	0.14352237	95.53805845	0.02064773	Trade	Coinbase Pro	EUR	EUR	\N	BTC	122	\N
165	\N	2019-04-09 13:10:53	0.28433211	189.27041103	0.04090521	Trade	Coinbase Pro	EUR	EUR	\N	BTC	123	\N
166	\N	2019-04-12 08:55:19	\N	0.00844382	0.04926535	Trade	Binance	\N	BNB	125	GAS	124	\N
167	\N	2019-04-23 06:27:48	\N	2.56039984	\N	Gift	Coinbase	\N	BAT	126	\N	\N	\N
168	\N	2019-04-23 06:29:46	\N	2.55927931	\N	Gift	Coinbase	\N	BAT	127	\N	\N	\N
169	\N	2019-04-23 06:32:14	\N	2.5599443	\N	Gift	Coinbase	\N	BAT	128	\N	\N	\N
170	\N	2019-04-23 06:35:33	\N	17.9648633	\N	Gift	Coinbase	\N	BAT	129	\N	\N	\N
171	\N	2019-05-04 12:55:21	\N	0.00486186	\N	Income	Binance	\N	GAS	130	\N	\N	\N
172	\N	2019-06-04 20:44:56	\N	0.00502392	\N	Income	Binance	\N	GAS	131	\N	\N	\N
173	\N	2019-06-26 14:28:40	19.18	3.94644775	500	Trade	Coinbase	EUR	LTC	132	EUR	\N	\N
174	\N	2019-06-27 11:12:05	0.28026195	111.82451737	1.21853021	Trade	Coinbase Pro	EUR	EUR	\N	LTC	133	\N
175	\N	2019-06-27 11:12:05	0.0253	10.0947	0.11	Trade	Coinbase Pro	EUR	EUR	\N	LTC	134	\N
176	\N	2019-06-27 11:12:05	0.3565	142.2435	1.55	Trade	Coinbase Pro	EUR	EUR	\N	LTC	135	\N
177	\N	2019-06-27 11:12:05	0.10676395	42.59881725	0.4641911	Trade	Coinbase Pro	EUR	EUR	\N	LTC	136	\N
178	\N	2019-06-27 11:12:05	0.023	9.177	0.1	Trade	Coinbase Pro	EUR	EUR	\N	LTC	137	\N
179	\N	2019-06-27 11:12:05	0.03243	12.93957	0.141	Trade	Coinbase Pro	EUR	EUR	\N	LTC	138	\N
180	\N	2019-06-27 11:12:05	0.062415	24.90358544	0.27136957	Trade	Coinbase Pro	EUR	EUR	\N	LTC	139	\N
181	\N	2019-06-27 11:12:05	0.02101208	8.38381996	0.09135687	Trade	Coinbase Pro	EUR	EUR	\N	LTC	140	\N
182	\N	2019-06-27 21:38:23	\N	0.3516	\N	Gift	Coinbase	\N	EOS	141	\N	\N	\N
183	\N	2019-06-27 22:38:14	\N	0.3582	\N	Gift	Coinbase	\N	EOS	142	\N	\N	\N
184	\N	2019-06-27 22:40:23	\N	0.3585	\N	Gift	Coinbase	\N	EOS	143	\N	\N	\N
185	\N	2019-06-27 22:43:29	\N	0.3558	\N	Gift	Coinbase	\N	EOS	144	\N	\N	\N
186	\N	2019-06-27 22:46:02	\N	0.3537	\N	Gift	Coinbase	\N	EOS	145	\N	\N	\N
187	\N	2019-06-28 10:22:08	0.90316708	3.56525049	362.16999923	Trade	Coinbase Pro	EUR	LTC	146	EUR	\N	\N
188	\N	2019-06-30 16:45:13	19.18	93.0914	500	Trade	Coinbase	EUR	EOS	147	EUR	\N	\N
189	\N	2019-07-04 13:44:30	\N	0.00486186	\N	Income	Binance	\N	GAS	148	\N	\N	\N
190	\N	2019-07-11 06:17:43	0.80218136	320.07036274	3.56525049	Trade	Coinbase Pro	EUR	EUR	\N	LTC	149	\N
191	\N	2019-07-12 09:56:15	0.719608	3200	288.562808	Trade	Coinbase Pro	EUR	XLM	150	EUR	\N	\N
192	\N	2019-07-12 09:56:15	0.07848923	349	31.47418023	Trade	Coinbase Pro	EUR	XLM	151	EUR	\N	\N
193	\N	2019-07-14 13:52:50	0.68523205	273.40758695	3549	Trade	Coinbase Pro	EUR	EUR	\N	XLM	152	\N
194	\N	2019-07-14 15:51:58	0.67925	271.02075	71.5	Trade	Coinbase Pro	EUR	EUR	\N	EOS	153	\N
195	\N	2019-07-14 15:51:58	0.00665	2.65335	0.7	Trade	Coinbase Pro	EUR	EUR	\N	EOS	154	\N
196	\N	2019-07-14 15:51:58	0.0475	18.9525	5	Trade	Coinbase Pro	EUR	EUR	\N	EOS	155	\N
197	\N	2019-07-14 15:51:58	0.019	7.581	2	Trade	Coinbase Pro	EUR	EUR	\N	EOS	156	\N
198	\N	2019-07-14 15:51:58	0.1482	59.1318	15.6	Trade	Coinbase Pro	EUR	EUR	\N	EOS	157	\N
199	\N	2019-07-16 22:00:15	0.29925187	1.67765589	119.99999962	Trade	Coinbase Pro	EUR	LTC	158	EUR	\N	\N
200	\N	2019-07-16 22:21:06	0.29925187	0.47323771	119.99999823	Trade	Coinbase Pro	EUR	BCH	159	EUR	\N	\N
201	\N	2019-07-18 10:21:35	0.178551	34	119.212551	Trade	Coinbase Pro	EUR	EOS	160	EUR	\N	\N
202	\N	2019-08-03 02:34:20	\N	0.00502392	\N	Income	Binance	\N	GAS	161	\N	\N	\N
203	\N	2019-08-10 21:51:00	\N	1.99983502	\N	Gift	Coinbase	\N	DAI	162	\N	\N	\N
204	\N	2019-08-10 21:53:38	\N	1.99983502	\N	Gift	Coinbase	\N	DAI	163	\N	\N	\N
205	\N	2019-08-10 21:56:12	\N	1.99983502	\N	Gift	Coinbase	\N	DAI	164	\N	\N	\N
206	\N	2019-08-10 22:10:10	\N	13.99930004	\N	Gift	Coinbase	\N	DAI	165	\N	\N	\N
207	\N	2019-08-13 18:06:58	0.03611723	0.18944259	14.48300914	Trade	Coinbase Pro	EUR	LTC	166	EUR	\N	\N
208	\N	2019-08-13 18:06:58	0.26313464	1.37983555	105.51699039	Trade	Coinbase Pro	EUR	LTC	167	EUR	\N	\N
209	\N	2019-08-14 18:27:48	0.06241887	24.90512973	0.3643302	Trade	Coinbase Pro	EUR	EUR	\N	LTC	168	\N
210	\N	2019-08-14 18:27:48	0.20643771	82.36864462	1.20494794	Trade	Coinbase Pro	EUR	EUR	\N	LTC	169	\N
211	\N	2019-08-19 00:24:34	0.22048174	1.2655	88.41317674	Trade	Coinbase Pro	EUR	LTC	170	EUR	\N	\N
212	\N	2019-08-19 00:24:34	0.07877013	0.45179313	31.58682302	Trade	Coinbase Pro	EUR	LTC	171	EUR	\N	\N
213	\N	2019-08-19 00:51:39	4.6	0.65409211	120	Trade	Coinbase	EUR	ETH	172	EUR	\N	\N
214	\N	2019-08-21 14:25:43	0.27459517	109.56347342	1.71729313	Trade	Coinbase Pro	EUR	EUR	\N	LTC	173	\N
215	\N	2019-08-21 14:31:18	3.25e-06	0.00129653	7.83e-06	Trade	Coinbase Pro	EUR	EUR	\N	ETH	174	\N
216	\N	2019-08-21 14:31:18	0.249375	99.50062493	0.60137457	Trade	Coinbase Pro	EUR	EUR	\N	ETH	175	\N
217	\N	2019-08-21 14:31:18	0.0218574	8.7211022	0.05270971	Trade	Coinbase Pro	EUR	EUR	\N	ETH	176	\N
218	\N	2019-08-23 16:08:28	0.29963643	0.673	120.15420643	Trade	Coinbase Pro	EUR	ETH	177	EUR	\N	\N
219	\N	2019-08-28 20:02:22	0.016675	6.653325	2.3	Trade	Coinbase Pro	EUR	EUR	\N	EOS	178	\N
220	\N	2019-08-28 20:02:22	0.21675	86.48325	30	Trade	Coinbase Pro	EUR	EUR	\N	EOS	179	\N
221	\N	2019-08-28 20:02:22	0.0122485	4.8871515	1.7	Trade	Coinbase Pro	EUR	EUR	\N	EOS	180	\N
222	\N	2019-08-28 20:14:24	1.53e-06	0.00061194	1.034e-05	Trade	Coinbase Pro	EUR	EUR	\N	LTC	181	\N
223	\N	2019-08-28 20:14:24	0.24875289	99.25240468	1.67764555	Trade	Coinbase Pro	EUR	EUR	\N	LTC	182	\N
224	\N	2019-08-29 20:39:39	0.2921	40	117.1321	Trade	Coinbase Pro	EUR	EOS	183	EUR	\N	\N
225	\N	2019-09-01 21:37:57	0.3025	2	121.3025	Trade	Coinbase Pro	EUR	LTC	184	EUR	\N	\N
226	\N	2019-09-04 16:12:41	\N	0.00502392	\N	Income	Binance	\N	GAS	185	\N	\N	\N
227	\N	2019-09-09 11:42:17	0.2106	140.1894	40	Trade	Coinbase Pro	EUR	EUR	\N	EOS	186	\N
228	\N	2019-09-19 22:43:19	0.3406365	135.91396496	0.47323771	Trade	Coinbase Pro	EUR	EUR	\N	BCH	187	\N
229	\N	2019-09-19 22:43:33	3.7e-07	0.00014661	2.14e-06	Trade	Coinbase Pro	EUR	EUR	\N	LTC	188	\N
230	\N	2019-09-19 22:43:33	0.17165	68.48835	1	Trade	Coinbase Pro	EUR	EUR	\N	LTC	189	\N
231	\N	2019-09-19 22:43:33	0.17154963	68.44830352	0.99999786	Trade	Coinbase Pro	EUR	EUR	\N	LTC	190	\N
232	\N	2019-09-20 13:09:38	0.3278183	130.7995017	0.673	Trade	Coinbase Pro	EUR	EUR	\N	ETH	191	\N
233	\N	2019-09-23 14:42:05	0.09942343	0.14313768	39.86879644	Trade	Coinbase Pro	EUR	BCH	192	EUR	\N	\N
234	\N	2019-09-23 14:42:05	0.22476609	0.32359069	90.1312034	Trade	Coinbase Pro	EUR	BCH	193	EUR	\N	\N
235	\N	2019-09-23 21:09:17	0.30567936	1.86049521	122.57742456	Trade	Coinbase Pro	EUR	LTC	194	EUR	\N	\N
236	\N	2019-09-23 21:09:17	0.04344782	0.26440175	17.42257485	Trade	Coinbase Pro	EUR	LTC	195	EUR	\N	\N
237	\N	2019-09-23 22:59:17	0.33881482	135.18711329	2.12489696	Trade	Coinbase Pro	EUR	EUR	\N	LTC	196	\N
238	\N	2019-09-23 23:18:43	0.31387483	125.2360567	0.46672837	Trade	Coinbase Pro	EUR	EUR	\N	BCH	197	\N
239	\N	2019-09-26 12:40:27	0.33665835	2.57334877	134.99999949	Trade	Coinbase Pro	EUR	LTC	198	EUR	\N	\N
240	\N	2019-09-26 18:22:55	0.09913984	39.5567957	0.82996935	Trade	Coinbase Pro	EUR	EUR	\N	LTC	199	\N
241	\N	2019-09-26 18:22:55	0.1443335	57.58906639	1.20857023	Trade	Coinbase Pro	EUR	EUR	\N	LTC	200	\N
242	\N	2019-09-26 18:22:55	0.06384285	25.47329598	0.53480919	Trade	Coinbase Pro	EUR	EUR	\N	LTC	201	\N
243	\N	2019-09-27 08:33:17	0.06259984	0.4995	25.10253484	Trade	Coinbase Pro	EUR	LTC	202	EUR	\N	\N
244	\N	2019-09-27 08:33:17	0.26158969	2.08687426	104.89746508	Trade	Coinbase Pro	EUR	LTC	203	EUR	\N	\N
245	\N	2019-09-27 08:43:37	0.32418952	0.6623209	129.99999853	Trade	Coinbase Pro	EUR	BCH	204	EUR	\N	\N
246	\N	2019-10-02 20:22:32	\N	0.00416731	\N	Income	Binance	\N	GAS	205	\N	\N	\N
247	\N	2019-10-23 14:41:52	0.20475217	81.69611619	1.78045366	Trade	Coinbase Pro	EUR	EUR	\N	LTC	206	\N
248	\N	2019-10-23 14:41:52	0.09268087	36.97966673	0.8059206	Trade	Coinbase Pro	EUR	EUR	\N	LTC	207	\N
249	\N	2019-10-23 17:56:51	0.30832694	123.02244785	0.6623209	Trade	Coinbase Pro	EUR	EUR	\N	BCH	208	\N
250	\N	2019-11-05 13:00:19	\N	0.00430622	\N	Income	Binance	\N	GAS	209	\N	\N	\N
251	\N	2019-11-05 16:20:15	0.057	0.2	11.457	Trade	Coinbase Pro	EUR	LTC	210	EUR	\N	\N
252	\N	2019-11-05 16:20:15	0.58976617	2.06862914	118.54299973	Trade	Coinbase Pro	EUR	LTC	211	EUR	\N	\N
253	\N	2019-11-15 15:16:50	0.5226	103.9974	2	Trade	Coinbase Pro	EUR	EUR	\N	LTC	212	\N
254	\N	2019-11-15 15:16:50	0.07011221	13.9523289	0.26862914	Trade	Coinbase Pro	EUR	EUR	\N	LTC	213	\N
255	\N	2019-11-22 09:06:50	0.64676617	0.88616314	129.99999971	Trade	Coinbase Pro	EUR	ETH	214	EUR	\N	\N
256	\N	2019-11-22 09:07:00	0.02474724	0.10701511	4.97419608	Trade	Coinbase Pro	EUR	LTC	215	EUR	\N	\N
257	\N	2019-11-22 09:07:00	0.62201892	2.6869068	125.02580376	Trade	Coinbase Pro	EUR	LTC	216	EUR	\N	\N
258	\N	2019-11-22 09:07:08	0.64676617	0.63302943	129.99999989	Trade	Coinbase Pro	EUR	BCH	217	EUR	\N	\N
259	\N	2019-11-22 09:07:17	0.64676617	0.0187117	129.9999998	Trade	Coinbase Pro	EUR	BTC	218	EUR	\N	\N
260	\N	2019-11-22 11:04:05	4e-08	7.22e-06	1.7e-07	Trade	Coinbase Pro	EUR	EUR	\N	LTC	219	\N
261	\N	2019-11-22 11:04:05	0.5963626	118.67615649	2.79392174	Trade	Coinbase Pro	EUR	EUR	\N	LTC	220	\N
262	\N	2019-11-22 15:04:04	0.57401217	114.22842261	0.88616314	Trade	Coinbase Pro	EUR	EUR	\N	ETH	221	\N
263	\N	2019-11-22 15:09:47	0.56184527	111.80720885	0.63302943	Trade	Coinbase Pro	EUR	EUR	\N	BCH	222	\N
264	\N	2019-11-22 15:10:24	0.5800627	115.4324773	0.0187117	Trade	Coinbase Pro	EUR	EUR	\N	BTC	223	\N
265	\N	2019-11-25 17:04:27	0.00016057	4.83e-06	0.03227524	Trade	Coinbase Pro	EUR	BTC	224	EUR	\N	\N
266	\N	2019-11-25 17:04:27	0.09974985	0.003	20.04971985	Trade	Coinbase Pro	EUR	BTC	225	EUR	\N	\N
267	\N	2019-11-25 17:04:27	0.5650894	0.01699517	113.5829699	Trade	Coinbase Pro	EUR	BTC	226	EUR	\N	\N
268	\N	2019-11-26 04:53:44	0.59875	50	120.34875	Trade	Coinbase Pro	EUR	EOS	227	EUR	\N	\N
269	\N	2019-11-26 04:53:44	0.0599	5	12.0399	Trade	Coinbase Pro	EUR	EOS	228	EUR	\N	\N
270	\N	2019-11-27 10:33:18	0.625175	124.409825	0.02	Trade	Coinbase Pro	EUR	EUR	\N	BTC	229	\N
271	\N	2019-11-27 16:43:08	0.6659	0.02	133.8459	Trade	Coinbase Pro	EUR	BTC	230	EUR	\N	\N
272	\N	2019-11-27 17:50:37	0.6625775	0.95	133.1780775	Trade	Coinbase Pro	EUR	ETH	231	EUR	\N	\N
273	\N	2019-11-27 17:50:58	0.66015	3	132.69015	Trade	Coinbase Pro	EUR	LTC	232	EUR	\N	\N
274	\N	2019-11-28 10:14:41	0.14865955	0.14902466	29.88056947	Trade	Coinbase Pro	EUR	BCH	233	EUR	\N	\N
275	\N	2019-11-28 10:14:41	0.2873106	0.2880018	57.74942973	Trade	Coinbase Pro	EUR	BCH	234	EUR	\N	\N
276	\N	2019-12-04 01:37:08	1.71e-06	0.00033994	8.61e-06	Trade	Coinbase Pro	EUR	EUR	\N	LTC	235	\N
277	\N	2019-12-04 01:37:08	0.12998988	25.8679854	0.65568664	Trade	Coinbase Pro	EUR	EUR	\N	LTC	236	\N
278	\N	2019-12-04 01:37:08	0.46475842	92.48692492	2.34430475	Trade	Coinbase Pro	EUR	EUR	\N	LTC	237	\N
279	\N	2019-12-04 09:59:03	0.15221695	0.75261781	30.59560737	Trade	Coinbase Pro	EUR	LTC	238	EUR	\N	\N
280	\N	2019-12-04 09:59:03	0.15225458	0.75261781	30.60317118	Trade	Coinbase Pro	EUR	LTC	239	EUR	\N	\N
281	\N	2019-12-04 09:59:03	0.15229221	0.75261781	30.61073498	Trade	Coinbase Pro	EUR	LTC	240	EUR	\N	\N
282	\N	2019-12-04 09:59:03	0.13209197	0.65262832	26.55048637	Trade	Coinbase Pro	EUR	LTC	241	EUR	\N	\N
283	\N	2019-12-05 17:05:21	\N	0.00416731	\N	Income	Binance	\N	GAS	242	\N	\N	\N
284	\N	2019-12-16 19:29:26	0.57836	115.09364	0.95	Trade	Coinbase Pro	EUR	EUR	\N	ETH	243	\N
285	\N	2019-12-16 19:29:30	0.59455	118.31545	55	Trade	Coinbase Pro	EUR	EUR	\N	EOS	244	\N
286	\N	2019-12-16 19:39:58	0.37321689	74.27016131	2.09085093	Trade	Coinbase Pro	EUR	EUR	\N	LTC	245	\N
287	\N	2019-12-16 19:40:13	0.1463041	29.11451617	0.81963082	Trade	Coinbase Pro	EUR	EUR	\N	LTC	246	\N
288	\N	2019-12-18 13:52:59	0.00055007	0.10946473	1.871e-05	Trade	Coinbase Pro	EUR	EUR	\N	BTC	247	\N
289	\N	2019-12-18 13:52:59	0.12999994	25.86998875	0.00442203	Trade	Coinbase Pro	EUR	EUR	\N	BTC	248	\N
290	\N	2019-12-18 13:52:59	0.45736445	91.01552509	0.01555926	Trade	Coinbase Pro	EUR	EUR	\N	BTC	249	\N
291	\N	2019-12-18 20:59:18	0.5433	3	109.2033	Trade	Coinbase Pro	EUR	LTC	250	EUR	\N	\N
292	\N	2019-12-18 21:18:24	1.69823771	0.05347938	341.34578009	Trade	Coinbase Pro	EUR	BTC	251	EUR	\N	\N
293	\N	2020-03-28 20:20:20.21	0.49	97.51	50	Trade	Coinbase Pro	EUR	EUR	\N	EOS	252	\N
294	\N	2020-03-28 20:20:20.241	0.49	97.51	50	Trade	Coinbase Pro	EUR	EUR	\N	EOS	253	\N
295	\N	2020-03-28 20:20:20.254	0.54096	107.65104	55.2	Trade	Coinbase Pro	EUR	EUR	\N	EOS	254	\N
768	\N	2021-09-01 12:44:10	0	0.00108613	\N	Income	Coinbase	EUR	DAI	657	\N	\N	\N
296	\N	2020-03-28 20:20:20.323	0.49	97.51	50	Trade	Coinbase Pro	EUR	EUR	\N	EOS	255	\N
297	\N	2020-05-03 18:36:43.573	1.4693265	114.3	295.3346265	Trade	Coinbase Pro	EUR	EOS	256	EUR	\N	\N
298	\N	2020-05-03 18:36:43.573	0.349656	27.2	70.280856	Trade	Coinbase Pro	EUR	EOS	257	EUR	\N	\N
299	\N	2020-05-10 00:17:39.05	1.6208825	322.5556175	141.5	Trade	Coinbase Pro	EUR	EUR	\N	EOS	258	\N
300	\N	2020-06-27 20:17:55.042	0.4947	48.5	99.4347	Trade	Coinbase Pro	EUR	EOS	259	EUR	\N	\N
301	\N	2020-06-27 20:17:55.045	1.02	100	205.02	Trade	Coinbase Pro	EUR	EOS	260	EUR	\N	\N
302	\N	2020-07-30 21:38:22.38	0.04662	9.27738	3.6	Trade	Coinbase Pro	EUR	EUR	\N	EOS	261	\N
303	\N	2020-07-30 21:38:22.38	1.876455	373.414545	144.9	Trade	Coinbase Pro	EUR	EUR	\N	EOS	262	\N
304	\N	2020-01-11 07:46:31.236	1.1575138e-05	0.002303452462	9.53e-06	Trade	Coinbase Pro	EUR	EUR	\N	BCH	263	\N
305	\N	2020-01-11 07:46:31.236	0.529926729318	105.455419134282	0.43701693	Trade	Coinbase Pro	EUR	EUR	\N	BCH	264	\N
306	\N	2020-02-18 07:41:38.497	1.492537306112	0.78313472	299.999998528512	Trade	Coinbase Pro	EUR	BCH	265	EUR	\N	\N
307	\N	2020-02-20 16:06:18.11	1.1947895552	237.7631214848	0.70907392	Trade	Coinbase Pro	EUR	EUR	\N	BCH	266	\N
308	\N	2020-02-20 16:06:18.11	0.124792448	24.833697152	0.0740608	Trade	Coinbase Pro	EUR	EUR	\N	BCH	267	\N
309	\N	2020-02-20 16:37:23.654	1.3064536125	0.77823	262.5971761125	Trade	Coinbase Pro	EUR	BCH	268	EUR	\N	\N
310	\N	2020-02-20 16:37:23.669	9.401e-07	5.6e-07	0.0001889601	Trade	Coinbase Pro	EUR	BCH	269	EUR	\N	\N
311	\N	2020-02-26 08:26:37.111	1.135866413848	226.037416355752	0.77823056	Trade	Coinbase Pro	EUR	EUR	\N	BCH	270	\N
312	\N	2020-02-26 16:16:42.664	0.99755	0.71	200.50755	Trade	Coinbase Pro	EUR	BCH	271	EUR	\N	\N
313	\N	2020-03-09 15:43:51.19	0.7642795	152.0916205	0.71	Trade	Coinbase Pro	EUR	EUR	\N	BCH	272	\N
314	\N	2020-03-12 22:13:30.164	0.995024874375	1.26352365	199.999999749375	Trade	Coinbase Pro	EUR	BCH	273	EUR	\N	\N
315	\N	2020-03-12 23:28:42.722	0.835820894475	166.328358000525	1.26352365	Trade	Coinbase Pro	EUR	EUR	\N	BCH	274	\N
316	\N	2020-03-28 03:37:19.324	2.2569316818	449.1294046782	2.42680826	Trade	Coinbase Pro	EUR	EUR	\N	BCH	275	\N
317	\N	2020-04-07 07:17:23.349	3.482587054896	2.92359558	699.999998034096	Trade	Coinbase Pro	EUR	BCH	276	EUR	\N	\N
318	\N	2020-04-07 14:15:48.964	0.7665916736525	152.5517430568475	0.65534659	Trade	Coinbase Pro	EUR	EUR	\N	BCH	277	\N
319	\N	2020-04-07 14:15:48.982	2.65157256	527.66293944	2.26824	Trade	Coinbase Pro	EUR	EUR	\N	BCH	278	\N
320	\N	2020-04-07 14:15:49.013	1.050931e-05	0.00209135269	8.99e-06	Trade	Coinbase Pro	EUR	EUR	\N	BCH	279	\N
321	\N	2020-04-09 18:47:24.113	1.15920396975	0.98655657	232.99999791975	Trade	Coinbase Pro	EUR	BCH	280	EUR	\N	\N
322	\N	2020-04-10 04:00:23.772	1.097840151096	218.470190068104	0.98655657	Trade	Coinbase Pro	EUR	EUR	\N	BCH	281	\N
323	\N	2020-04-27 15:04:38.828	1.751243770732	1.60121036	351.999997917132	Trade	Coinbase Pro	EUR	BCH	282	EUR	\N	\N
324	\N	2020-05-03 18:31:49.2	1.820895518405	1.5859387	365.999999199405	Trade	Coinbase Pro	EUR	BCH	283	EUR	\N	\N
325	\N	2020-05-11 18:13:10.083	1.4967379063065	297.8508433549935	1.45462647	Trade	Coinbase Pro	EUR	EUR	\N	BCH	284	\N
326	\N	2020-05-11 18:13:10.083	0.1351137190585	26.8876300926415	0.13131223	Trade	Coinbase Pro	EUR	EUR	\N	BCH	285	\N
327	\N	2020-06-27 19:47:14.431	1.57599129683	313.62226806917	1.60121036	Trade	Coinbase Pro	EUR	EUR	\N	BCH	286	\N
328	\N	2020-06-27 20:03:56.671	1.4925373125	1.5920398	299.9999998125	Trade	Coinbase Pro	EUR	BCH	287	EUR	\N	\N
329	\N	2020-07-30 21:13:50.815	1.99211940174	396.43176094626	1.5920398	Trade	Coinbase Pro	EUR	EUR	\N	BCH	288	\N
330	\N	2020-08-27 17:40:14.607	0.953403468195	0.86555013	191.634097107195	Trade	Coinbase Pro	EUR	BCH	289	EUR	\N	\N
331	\N	2020-08-27 17:41:43.909	0.099135	0.09	19.926135	Trade	Coinbase Pro	EUR	BCH	290	EUR	\N	\N
332	\N	2020-08-27 17:44:34.316	2.472261531805	2.24444987	496.924567892805	Trade	Coinbase Pro	EUR	BCH	291	EUR	\N	\N
333	\N	2020-09-03 23:30:47.332	0.1119	22.2681	0.12	Trade	Coinbase Pro	EUR	EUR	\N	BCH	292	\N
334	\N	2020-09-03 23:30:47.359	0.699375	139.175625	0.75	Trade	Coinbase Pro	EUR	EUR	\N	BCH	293	\N
335	\N	2020-09-03 23:30:47.362	2.172725	432.372275	2.33	Trade	Coinbase Pro	EUR	EUR	\N	BCH	294	\N
336	\N	2020-12-15 14:53:44.157	4.97512437788	2564.497102	999.99999995388	Trade	Coinbase Pro	EUR	XRP	295	EUR	\N	\N
337	\N	2020-12-22 23:44:05.227	3.4770736	2672	996.9266736	Trade	Coinbase Pro	EUR	XRP	296	EUR	\N	\N
338	\N	2020-12-23 01:40:46.76	3.1310496	891.4545504	2672	Trade	Coinbase Pro	EUR	EUR	\N	XRP	297	\N
339	\N	2020-12-23 16:48:16.236	1.7126942	487.6285058	1922	Trade	Coinbase Pro	EUR	EUR	\N	XRP	298	\N
340	\N	2020-12-23 16:48:16.236	0.5723042936065	162.9432081653935	642.497102	Trade	Coinbase Pro	EUR	EUR	\N	XRP	299	\N
341	\N	2020-01-14 20:25:18.541	0.386625	76.938375	1.5	Trade	Coinbase Pro	EUR	EUR	\N	LTC	300	\N
342	\N	2020-01-23 17:52:13.071	0.3573	71.1027	1.5	Trade	Coinbase Pro	EUR	EUR	\N	LTC	301	\N
343	\N	2020-03-12 21:56:41.65	0.2986011	1.998	60.0188211	Trade	Coinbase Pro	EUR	LTC	302	EUR	\N	\N
344	\N	2020-03-12 21:56:41.65	0.696423774995	4.65835301	139.981178773995	Trade	Coinbase Pro	EUR	LTC	303	EUR	\N	\N
345	\N	2020-03-13 01:45:32.487	0.3494970167	69.5499063233	2.68843859	Trade	Coinbase Pro	EUR	EUR	\N	LTC	304	\N
346	\N	2020-03-13 01:45:32.487	0.2489197061	49.5350215139	1.91476697	Trade	Coinbase Pro	EUR	EUR	\N	LTC	305	\N
347	\N	2020-03-13 01:45:32.487	0.013	2.587	0.1	Trade	Coinbase Pro	EUR	EUR	\N	LTC	306	\N
348	\N	2020-03-13 01:45:32.487	0.13	25.87	1	Trade	Coinbase Pro	EUR	EUR	\N	LTC	307	\N
349	\N	2020-03-13 01:45:32.487	0.1239091685	24.6579245315	0.95314745	Trade	Coinbase Pro	EUR	EUR	\N	LTC	308	\N
350	\N	2020-03-13 21:10:59.022	0.3278718	1.998	65.9022318	Trade	Coinbase Pro	EUR	LTC	309	EUR	\N	\N
351	\N	2020-03-13 21:10:59.022	1.6621779508775	10.12596985	334.0977681263775	Trade	Coinbase Pro	EUR	LTC	310	EUR	\N	\N
352	\N	2020-03-29 05:38:18.376	2.0822918217375	414.3760725257625	12.12396985	Trade	Coinbase Pro	EUR	EUR	\N	LTC	311	\N
353	\N	2020-04-07 12:16:30.848	1.282376281915	5.9136559	257.757632664915	Trade	Coinbase Pro	EUR	LTC	312	EUR	\N	\N
354	\N	2020-04-07 12:16:30.848	1.0840662	4.998	217.8973062	Trade	Coinbase Pro	EUR	LTC	313	EUR	\N	\N
355	\N	2020-04-07 12:16:30.848	0.673358512725	3.10446525	135.345061057725	Trade	Coinbase Pro	EUR	LTC	314	EUR	\N	\N
356	\N	2020-04-07 20:03:05.31	2.908345138625	578.760682586375	14.01612115	Trade	Coinbase Pro	EUR	EUR	\N	LTC	315	\N
357	\N	2020-09-03 23:29:47.407	3.53175	17	709.88175	Trade	Coinbase Pro	EUR	LTC	316	EUR	\N	\N
358	\N	2020-05-10 00:20:44.94	0.029589348	5.888280252	0.03396	Trade	Coinbase Pro	EUR	EUR	\N	ETH	317	\N
359	\N	2020-05-10 00:20:44.94	0.8711	173.3489	1	Trade	Coinbase Pro	EUR	EUR	\N	ETH	318	\N
360	\N	2020-05-10 00:20:44.94	0.0435525	8.6669475	0.05	Trade	Coinbase Pro	EUR	EUR	\N	ETH	319	\N
361	\N	2020-05-10 00:20:44.94	0.034822	6.929578	0.04	Trade	Coinbase Pro	EUR	EUR	\N	ETH	320	\N
362	\N	2020-05-10 00:20:44.94	0.6228304170945	123.9432530018055	0.71585589	Trade	Coinbase Pro	EUR	EUR	\N	ETH	321	\N
363	\N	2020-01-14 20:31:02.831	1.047038019423	208.360565865177	0.02673969	Trade	Coinbase Pro	EUR	EUR	\N	BTC	322	\N
364	\N	2020-02-16 16:53:04.582	0.443685	88.293315	0.01	Trade	Coinbase Pro	EUR	EUR	\N	BTC	323	\N
365	\N	2020-02-16 16:53:04.582	0.133095	26.485905	0.003	Trade	Coinbase Pro	EUR	EUR	\N	BTC	324	\N
366	\N	2020-02-20 09:56:20.852	0.102556116234	0.00230196	20.613779363034	Trade	Coinbase Pro	EUR	BTC	325	EUR	\N	\N
367	\N	2020-02-20 09:56:20.852	1.289881312182	0.02895246	259.266143748582	Trade	Coinbase Pro	EUR	BTC	326	EUR	\N	\N
368	\N	2020-02-25 16:48:13.698	0.5897810021415	117.3664194261585	0.01373969	Trade	Coinbase Pro	EUR	EUR	\N	BTC	327	\N
369	\N	2020-02-26 14:23:50.422	1.289638630692	256.638087507708	0.03125442	Trade	Coinbase Pro	EUR	EUR	\N	BTC	328	\N
370	\N	2020-03-01 07:10:16.733	1.987711094293	0.05113958	399.529929952893	Trade	Coinbase Pro	EUR	BTC	329	EUR	\N	\N
371	\N	2020-03-09 04:10:19.868	1.792442279	356.696013521	0.05113958	Trade	Coinbase Pro	EUR	EUR	\N	BTC	330	\N
372	\N	2020-12-16 13:44:22.728	4.1475	0.05	833.6475	Trade	Coinbase Pro	EUR	BTC	331	EUR	\N	\N
373	\N	2020-12-16 13:44:22.728	34.30229976	0.41328072	6894.76225176	Trade	Coinbase Pro	EUR	BTC	332	EUR	\N	\N
374	\N	2020-12-16 13:44:22.728	0.44073	0.00531	88.58673	Trade	Coinbase Pro	EUR	BTC	333	EUR	\N	\N
375	\N	2020-12-16 13:44:22.728	0.16617347	0.00200209	33.40086747	Trade	Coinbase Pro	EUR	BTC	334	EUR	\N	\N
376	\N	2020-12-16 13:44:22.728	0.45370207	0.00546629	91.19411607	Trade	Coinbase Pro	EUR	BTC	335	EUR	\N	\N
377	\N	2020-12-16 13:44:22.728	2.02496677	0.02439719	407.01832077	Trade	Coinbase Pro	EUR	BTC	336	EUR	\N	\N
378	\N	2020-12-16 23:41:16	\N	\N	0.50045629	Withdraw	Coinbase Pro	\N	\N	\N	BTC	\N	\N
379	\N	2020-12-16 23:41:16	\N	0.50042659	\N	Deposit	Phemex	\N	BTC	\N	\N	\N	\N
380	\N	2020-12-17 09:19:30	\N	11741.41310981	0.500426	Trade	Phemex	\N	USDT	338	BTC	337	\N
381	\N	2020-12-17 17:51:36	\N	0.501117	11741.38679031	Trade	Phemex	\N	BTC	340	USDT	339	\N
382	\N	2020-11-25 00:12:00	\N	10.6808508	\N	Gift	Coinbase	\N	XLM	341	\N	\N	\N
383	\N	2020-11-25 00:12:00	\N	10.6813642	\N	Gift	Coinbase	\N	XLM	342	\N	\N	\N
384	\N	2020-11-25 00:13:00	\N	10.7819618	\N	Gift	Coinbase	\N	XLM	343	\N	\N	\N
385	\N	2020-11-25 00:13:00	\N	10.7918522	\N	Gift	Coinbase	\N	XLM	344	\N	\N	\N
386	\N	2020-11-25 00:12:00	\N	10.6827906	\N	Gift	Coinbase	\N	XLM	345	\N	\N	\N
387	\N	2020-08-12 18:41:32	\N	0.33093574	\N	Income	NEON home Wallet	\N	GAS	346	\N	\N	\N
388	\N	2020-11-26 18:12:44	\N	0.0540425	\N	Income	NEON home Wallet	\N	GAS	347	\N	\N	\N
389	\N	2020-12-07 21:38:08	\N	0.0058387	\N	Income	NEON home Wallet	\N	GAS	348	\N	\N	\N
390	\N	2020-05-03 20:42:00	14.04	1.83981589	351.96	Trade	Coinbase	EUR	ETH	349	EUR	\N	\N
391	\N	2020-03-13 22:22:00	15.35	2.42680826	384.65	Trade	Coinbase	EUR	BCH	350	EUR	\N	\N
392	\N	2020-03-13 22:34:00	15.35	205.2225	384.65	Trade	Coinbase	EUR	EOS	351	EUR	\N	\N
393	\N	2020-12-27 12:15:11	5.2e-06	\N	2.9e-05	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
394	\N	2020-12-27 12:10:03	0.00011102	\N	0.00213239	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
395	\N	2020-12-27 11:43:18	0.00011379	\N	0.00061663	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
396	\N	2020-12-27 11:41:21	5.634e-05	\N	0.00043143	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
397	\N	2020-12-27 11:39:47	8.429e-05	\N	0.00136807	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
398	\N	2020-12-27 11:25:47	8.222e-05	\N	0.00015374	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
399	\N	2020-12-27 11:02:18	8.283e-05	\N	0.00098523	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
400	\N	2020-12-26 22:14:00	-3.261e-05	0.00234698	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
401	\N	2020-12-25 12:03:54	0.00045398	\N	0.00028146	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
402	\N	2020-12-25 11:38:11	0.00062715	\N	0.00273211	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
403	\N	2020-12-25 11:25:17	0.00027162	\N	0.00141611	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
404	\N	2020-12-25 10:19:45	0.00029926	0.00028909	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
405	\N	2020-12-25 10:06:57	0.00029838	\N	0.00106578	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
406	\N	2020-12-25 09:25:31	0.00016242	\N	0.00040325	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
407	\N	2020-12-25 09:22:02	0.00015997	9.983e-05	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
408	\N	2020-12-24 23:08:47	0.00017824	\N	0.0004265	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
409	\N	2020-12-24 22:45:05	0.00017816	\N	0.00055548	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
410	\N	2020-12-24 22:27:55	0.00017191	\N	0.00013864	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
411	\N	2020-12-24 22:22:32	0.0001723	0.00044557	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
412	\N	2020-12-24 21:52:28	0.00017732	\N	0.0002478	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
413	\N	2020-12-24 21:42:03	0.00017858	\N	6.462e-05	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
414	\N	2020-12-24 21:26:09	0.0001868	\N	0.00046074	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
415	\N	2020-12-24 00:05:15	7.994e-05	0.00024819	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
416	\N	2020-12-20 16:42:45	0.00014649	\N	0.00034514	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
741	\N	2021-01-19 17:48:42	\N	\N	0.1713421	Withdraw	Coinbase	\N	\N	\N	BTC	\N	\N
742	\N	2021-02-05 08:00:55	22.02	1.0549794	-1477.98	Trade	Coinbase	EUR	ETH	633	EUR	\N	\N
743	\N	2021-02-16 13:28:12	\N	\N	1.0549793	Withdraw	Coinbase	\N	\N	\N	ETH	\N	\N
744	\N	2021-01-19 18:22:55	\N	\N	17	Withdraw	Coinbase	\N	\N	\N	LTC	\N	\N
745	\N	2021-08-09 08:02:03	0	0.00108504	\N	Income	Coinbase	EUR	DAI	634	\N	\N	\N
746	\N	2021-08-10 10:11:44	0	0.00108504	\N	Income	Coinbase	EUR	DAI	635	\N	\N	\N
747	\N	2021-08-11 13:49:50	0	0.00108504	\N	Income	Coinbase	EUR	DAI	636	\N	\N	\N
748	\N	2021-08-12 14:19:44	0	0.00108504	\N	Income	Coinbase	EUR	DAI	637	\N	\N	\N
749	\N	2021-08-13 14:03:45	0	0.00108504	\N	Income	Coinbase	EUR	DAI	638	\N	\N	\N
750	\N	2021-08-14 10:17:08	0	0.00108507	\N	Income	Coinbase	EUR	DAI	639	\N	\N	\N
751	\N	2021-08-15 10:04:02	0	0.00108513	\N	Income	Coinbase	EUR	DAI	640	\N	\N	\N
752	\N	2021-08-16 09:27:51	0	0.00108518	\N	Income	Coinbase	EUR	DAI	641	\N	\N	\N
753	\N	2021-08-17 08:19:32	0	0.00108524	\N	Income	Coinbase	EUR	DAI	642	\N	\N	\N
754	\N	2021-08-18 13:07:15	0	0.00108529	\N	Income	Coinbase	EUR	DAI	643	\N	\N	\N
755	\N	2021-08-19 12:18:26	0	0.00108536	\N	Income	Coinbase	EUR	DAI	644	\N	\N	\N
756	\N	2021-08-20 09:56:11	0	0.00108542	\N	Income	Coinbase	EUR	DAI	645	\N	\N	\N
757	\N	2021-08-21 15:26:25	0	0.00108548	\N	Income	Coinbase	EUR	DAI	646	\N	\N	\N
758	\N	2021-08-23 03:46:08	0	0.00108554	\N	Income	Coinbase	EUR	DAI	647	\N	\N	\N
759	\N	2021-08-23 14:18:02	0	0.00108559	\N	Income	Coinbase	EUR	DAI	648	\N	\N	\N
760	\N	2021-08-24 09:15:01	0	0.00108565	\N	Income	Coinbase	EUR	DAI	649	\N	\N	\N
761	\N	2021-08-25 14:08:05	0	0.00108572	\N	Income	Coinbase	EUR	DAI	650	\N	\N	\N
762	\N	2021-08-26 09:15:48	0	0.00108576	\N	Income	Coinbase	EUR	DAI	651	\N	\N	\N
763	\N	2021-08-27 11:25:51	0	0.0010858	\N	Income	Coinbase	EUR	DAI	652	\N	\N	\N
764	\N	2021-08-28 14:04:33	0	0.00108587	\N	Income	Coinbase	EUR	DAI	653	\N	\N	\N
765	\N	2021-08-29 08:41:49	0	0.00108595	\N	Income	Coinbase	EUR	DAI	654	\N	\N	\N
766	\N	2021-08-30 13:54:09	0	0.001086	\N	Income	Coinbase	EUR	DAI	655	\N	\N	\N
767	\N	2021-08-31 15:14:48	0	0.00108607	\N	Income	Coinbase	EUR	DAI	656	\N	\N	\N
769	\N	2021-09-02 08:46:33	0	0.00108618	\N	Income	Coinbase	EUR	DAI	658	\N	\N	\N
770	\N	2021-09-03 12:32:35	0	0.00108625	\N	Income	Coinbase	EUR	DAI	659	\N	\N	\N
771	\N	2021-09-04 10:25:41	0	0.0010863	\N	Income	Coinbase	EUR	DAI	660	\N	\N	\N
772	\N	2021-09-05 11:12:54	0	0.00108635	\N	Income	Coinbase	EUR	DAI	661	\N	\N	\N
773	\N	2021-09-06 12:35:54	0	0.00108642	\N	Income	Coinbase	EUR	DAI	662	\N	\N	\N
774	\N	2021-09-07 09:48:41	0	0.00108649	\N	Income	Coinbase	EUR	DAI	663	\N	\N	\N
775	\N	2021-09-08 10:49:02	0	0.00108654	\N	Income	Coinbase	EUR	DAI	664	\N	\N	\N
776	\N	2021-09-09 07:58:39	0	0.0010866	\N	Income	Coinbase	EUR	DAI	665	\N	\N	\N
777	\N	2021-09-10 10:45:17	0	0.00108666	\N	Income	Coinbase	EUR	DAI	666	\N	\N	\N
778	\N	2021-09-11 07:55:34	0	0.00108671	\N	Income	Coinbase	EUR	DAI	667	\N	\N	\N
779	\N	2021-09-12 09:29:25	0	0.00108678	\N	Income	Coinbase	EUR	DAI	668	\N	\N	\N
780	\N	2021-09-13 08:31:24	0	0.00108683	\N	Income	Coinbase	EUR	DAI	669	\N	\N	\N
781	\N	2021-09-14 12:38:44	0	0.0010869	\N	Income	Coinbase	EUR	DAI	670	\N	\N	\N
782	\N	2021-09-15 08:55:09	0	0.00108695	\N	Income	Coinbase	EUR	DAI	671	\N	\N	\N
783	\N	2021-09-16 08:37:24	0	0.00108702	\N	Income	Coinbase	EUR	DAI	672	\N	\N	\N
784	\N	2021-09-17 09:25:56	0	0.00108707	\N	Income	Coinbase	EUR	DAI	673	\N	\N	\N
785	\N	2021-09-18 11:40:11	0	0.00108713	\N	Income	Coinbase	EUR	DAI	674	\N	\N	\N
786	\N	2021-09-19 12:47:19	0	0.00108718	\N	Income	Coinbase	EUR	DAI	675	\N	\N	\N
787	\N	2021-09-21 13:32:30	0	0.00108731	\N	Income	Coinbase	EUR	DAI	676	\N	\N	\N
788	\N	2021-09-22 11:55:11	0	0.00108737	\N	Income	Coinbase	EUR	DAI	677	\N	\N	\N
789	\N	2021-09-23 11:18:56	0	0.00108742	\N	Income	Coinbase	EUR	DAI	678	\N	\N	\N
790	\N	2021-09-24 13:47:47	0	0.00108748	\N	Income	Coinbase	EUR	DAI	679	\N	\N	\N
791	\N	2021-09-25 00:47:52	0	0.00108725	\N	Income	Coinbase	EUR	DAI	680	\N	\N	\N
792	\N	2021-09-25 13:08:49	0	0.00108751	\N	Income	Coinbase	EUR	DAI	681	\N	\N	\N
793	\N	2021-09-26 12:30:45	0	0.00108754	\N	Income	Coinbase	EUR	DAI	682	\N	\N	\N
794	\N	2021-09-27 12:26:31	0	0.0010876	\N	Income	Coinbase	EUR	DAI	683	\N	\N	\N
795	\N	2021-09-28 14:18:14	0	0.00108766	\N	Income	Coinbase	EUR	DAI	684	\N	\N	\N
796	\N	2021-09-29 10:32:58	0	0.00108771	\N	Income	Coinbase	EUR	DAI	685	\N	\N	\N
797	\N	2021-09-30 14:19:47	0	0.00108783	\N	Income	Coinbase	EUR	DAI	686	\N	\N	\N
798	\N	2021-10-01 11:30:07	0	0.00108789	\N	Income	Coinbase	EUR	DAI	687	\N	\N	\N
799	\N	2021-10-02 11:01:32	0	0.00108795	\N	Income	Coinbase	EUR	DAI	688	\N	\N	\N
800	\N	2021-10-03 08:59:23	0	0.00108801	\N	Income	Coinbase	EUR	DAI	689	\N	\N	\N
801	\N	2021-10-04 10:03:45	0	0.00108807	\N	Income	Coinbase	EUR	DAI	690	\N	\N	\N
802	\N	2021-10-05 11:37:57	0	0.00108812	\N	Income	Coinbase	EUR	DAI	691	\N	\N	\N
803	\N	2021-10-06 09:00:11	0	0.00108819	\N	Income	Coinbase	EUR	DAI	692	\N	\N	\N
804	\N	2021-10-07 09:01:35	0	0.00108825	\N	Income	Coinbase	EUR	DAI	693	\N	\N	\N
805	\N	2021-10-08 08:28:59	0	0.00108831	\N	Income	Coinbase	EUR	DAI	694	\N	\N	\N
806	\N	2021-10-09 08:06:41	0	0.00108837	\N	Income	Coinbase	EUR	DAI	695	\N	\N	\N
807	\N	2021-10-10 12:45:36	0	0.00108843	\N	Income	Coinbase	EUR	DAI	696	\N	\N	\N
808	\N	2021-10-11 15:20:57	0	0.00108849	\N	Income	Coinbase	EUR	DAI	697	\N	\N	\N
809	\N	2021-10-12 14:00:43	0	0.00108855	\N	Income	Coinbase	EUR	DAI	698	\N	\N	\N
810	\N	2021-10-13 08:48:00	0	0.00108861	\N	Income	Coinbase	EUR	DAI	699	\N	\N	\N
811	\N	2021-10-14 13:50:04	0	0.00108867	\N	Income	Coinbase	EUR	DAI	700	\N	\N	\N
812	\N	2021-10-15 10:05:54	0	0.00108872	\N	Income	Coinbase	EUR	DAI	701	\N	\N	\N
813	\N	2021-10-16 10:15:24	0	0.00108877	\N	Income	Coinbase	EUR	DAI	702	\N	\N	\N
814	\N	2021-10-17 11:32:14	0	0.00108883	\N	Income	Coinbase	EUR	DAI	703	\N	\N	\N
815	\N	2021-10-18 09:12:30	0	0.0010889	\N	Income	Coinbase	EUR	DAI	704	\N	\N	\N
816	\N	2021-10-19 08:43:23	0	0.00108895	\N	Income	Coinbase	EUR	DAI	705	\N	\N	\N
817	\N	2021-10-20 12:13:53	0	0.00108902	\N	Income	Coinbase	EUR	DAI	706	\N	\N	\N
818	\N	2021-10-21 08:23:12	0	0.00108908	\N	Income	Coinbase	EUR	DAI	707	\N	\N	\N
819	\N	2021-10-22 10:27:29	0	0.00108913	\N	Income	Coinbase	EUR	DAI	708	\N	\N	\N
820	\N	2021-10-23 08:24:18	0	0.0010892	\N	Income	Coinbase	EUR	DAI	709	\N	\N	\N
821	\N	2021-10-24 14:31:04	0	0.00108926	\N	Income	Coinbase	EUR	DAI	710	\N	\N	\N
822	\N	2021-10-25 12:20:53	0	0.00108931	\N	Income	Coinbase	EUR	DAI	711	\N	\N	\N
823	\N	2021-10-26 08:36:09	0	0.00108938	\N	Income	Coinbase	EUR	DAI	712	\N	\N	\N
824	\N	2021-10-27 08:20:13	0	0.00108943	\N	Income	Coinbase	EUR	DAI	713	\N	\N	\N
825	\N	2021-10-28 10:13:49	0	0.0010895	\N	Income	Coinbase	EUR	DAI	714	\N	\N	\N
826	\N	2021-10-29 12:32:35	0	0.00108954	\N	Income	Coinbase	EUR	DAI	715	\N	\N	\N
827	\N	2021-10-30 12:46:04	0	0.0010896	\N	Income	Coinbase	EUR	DAI	716	\N	\N	\N
828	\N	2021-10-31 15:47:51	0	0.00108967	\N	Income	Coinbase	EUR	DAI	717	\N	\N	\N
829	\N	2021-11-01 12:07:38	0	0.00108973	\N	Income	Coinbase	EUR	DAI	718	\N	\N	\N
830	\N	2021-11-02 11:52:40	0	0.00108979	\N	Income	Coinbase	EUR	DAI	719	\N	\N	\N
831	\N	2021-11-03 11:21:25	0	0.00108984	\N	Income	Coinbase	EUR	DAI	720	\N	\N	\N
832	\N	2021-11-04 13:42:19	0	0.0010899	\N	Income	Coinbase	EUR	DAI	721	\N	\N	\N
833	\N	2021-11-05 09:50:19	0	0.00108995	\N	Income	Coinbase	EUR	DAI	722	\N	\N	\N
834	\N	2021-11-06 08:36:22	0	0.00109002	\N	Income	Coinbase	EUR	DAI	723	\N	\N	\N
835	\N	2021-11-07 10:31:09	0	0.00109008	\N	Income	Coinbase	EUR	DAI	724	\N	\N	\N
836	\N	2021-11-08 14:30:00	0	0.00109014	\N	Income	Coinbase	EUR	DAI	725	\N	\N	\N
837	\N	2021-11-09 14:59:40	0	0.00109019	\N	Income	Coinbase	EUR	DAI	726	\N	\N	\N
838	\N	2021-11-10 14:37:38	0	0.00109026	\N	Income	Coinbase	EUR	DAI	727	\N	\N	\N
839	\N	2021-11-11 11:13:04	0	0.00109032	\N	Income	Coinbase	EUR	DAI	728	\N	\N	\N
840	\N	2021-11-12 10:59:39	0	0.00109038	\N	Income	Coinbase	EUR	DAI	729	\N	\N	\N
841	\N	2021-11-13 14:34:16	0	0.00109043	\N	Income	Coinbase	EUR	DAI	730	\N	\N	\N
842	\N	2021-11-14 08:30:45	0	0.00109049	\N	Income	Coinbase	EUR	DAI	731	\N	\N	\N
843	\N	2021-11-15 09:29:55	0	0.00109055	\N	Income	Coinbase	EUR	DAI	732	\N	\N	\N
844	\N	2021-11-16 12:14:01	0	0.00109061	\N	Income	Coinbase	EUR	DAI	733	\N	\N	\N
845	\N	2021-11-17 14:08:38	0	0.00109067	\N	Income	Coinbase	EUR	DAI	734	\N	\N	\N
846	\N	2021-11-18 09:28:08	0	0.00109072	\N	Income	Coinbase	EUR	DAI	735	\N	\N	\N
847	\N	2021-11-19 12:52:41	0	0.0010908	\N	Income	Coinbase	EUR	DAI	736	\N	\N	\N
848	\N	2021-11-20 14:10:31	0	0.00109085	\N	Income	Coinbase	EUR	DAI	737	\N	\N	\N
849	\N	2021-11-21 12:14:45	0	0.00109091	\N	Income	Coinbase	EUR	DAI	738	\N	\N	\N
850	\N	2021-11-22 12:11:08	0	0.00109096	\N	Income	Coinbase	EUR	DAI	739	\N	\N	\N
851	\N	2021-11-23 11:52:59	0	0.00109103	\N	Income	Coinbase	EUR	DAI	740	\N	\N	\N
852	\N	2021-11-24 13:16:02	0	0.00109108	\N	Income	Coinbase	EUR	DAI	741	\N	\N	\N
853	\N	2021-11-25 11:56:57	0	0.00109114	\N	Income	Coinbase	EUR	DAI	742	\N	\N	\N
854	\N	2021-11-26 11:57:29	0	0.0010912	\N	Income	Coinbase	EUR	DAI	743	\N	\N	\N
855	\N	2021-11-27 10:12:28	0	0.00109126	\N	Income	Coinbase	EUR	DAI	744	\N	\N	\N
856	\N	2021-11-28 08:42:04	0	0.00109132	\N	Income	Coinbase	EUR	DAI	745	\N	\N	\N
857	\N	2021-11-29 08:59:47	0	0.00109138	\N	Income	Coinbase	EUR	DAI	746	\N	\N	\N
858	\N	2021-11-30 14:18:31	0	0.00109144	\N	Income	Coinbase	EUR	DAI	747	\N	\N	\N
859	\N	2021-12-01 08:32:47	0	0.0010915	\N	Income	Coinbase	EUR	DAI	748	\N	\N	\N
860	\N	2021-12-02 09:00:29	0	0.00109156	\N	Income	Coinbase	EUR	DAI	749	\N	\N	\N
861	\N	2021-12-03 15:09:37	0	0.00109163	\N	Income	Coinbase	EUR	DAI	750	\N	\N	\N
862	\N	2021-12-04 08:40:23	0	0.00109168	\N	Income	Coinbase	EUR	DAI	751	\N	\N	\N
863	\N	2021-12-05 12:52:48	0	0.00109173	\N	Income	Coinbase	EUR	DAI	752	\N	\N	\N
864	\N	2021-12-06 12:04:06	0	0.0010918	\N	Income	Coinbase	EUR	DAI	753	\N	\N	\N
865	\N	2021-12-07 09:53:54	0	0.00109186	\N	Income	Coinbase	EUR	DAI	754	\N	\N	\N
866	\N	2021-12-08 13:03:32	0	0.00109191	\N	Income	Coinbase	EUR	DAI	755	\N	\N	\N
867	\N	2021-12-09 09:41:38	0	0.00109198	\N	Income	Coinbase	EUR	DAI	756	\N	\N	\N
868	\N	2021-12-10 13:26:19	0	0.00109203	\N	Income	Coinbase	EUR	DAI	757	\N	\N	\N
869	\N	2021-12-11 10:38:44	0	0.00109209	\N	Income	Coinbase	EUR	DAI	758	\N	\N	\N
870	\N	2021-12-12 12:11:06	0	0.00109216	\N	Income	Coinbase	EUR	DAI	759	\N	\N	\N
871	\N	2021-12-13 09:51:39	0	0.00109221	\N	Income	Coinbase	EUR	DAI	760	\N	\N	\N
872	\N	2021-12-14 13:39:13	0	0.00109227	\N	Income	Coinbase	EUR	DAI	761	\N	\N	\N
873	\N	2021-12-15 08:43:54	0	0.00109232	\N	Income	Coinbase	EUR	DAI	762	\N	\N	\N
874	\N	2021-12-16 12:53:24	0	0.00109239	\N	Income	Coinbase	EUR	DAI	763	\N	\N	\N
875	\N	2021-12-17 11:40:17	0	0.00109245	\N	Income	Coinbase	EUR	DAI	764	\N	\N	\N
876	\N	2021-12-18 12:16:01	0	0.00109251	\N	Income	Coinbase	EUR	DAI	765	\N	\N	\N
877	\N	2021-12-19 16:36:29	0	0.00109256	\N	Income	Coinbase	EUR	DAI	766	\N	\N	\N
878	\N	2021-12-21 01:26:57	0	0.00109263	\N	Income	Coinbase	EUR	DAI	767	\N	\N	\N
879	\N	2021-12-21 21:58:41	0	0.00109268	\N	Income	Coinbase	EUR	DAI	768	\N	\N	\N
880	\N	2021-12-23 00:20:39	0	0.00109274	\N	Income	Coinbase	EUR	DAI	769	\N	\N	\N
881	\N	2021-12-23 11:53:48	0	0.0010928	\N	Income	Coinbase	EUR	DAI	770	\N	\N	\N
882	\N	2021-12-24 18:26:54	0	0.00109285	\N	Income	Coinbase	EUR	DAI	771	\N	\N	\N
883	\N	2021-12-25 10:19:56	0	0.00109289	\N	Income	Coinbase	EUR	DAI	772	\N	\N	\N
884	\N	2021-12-26 09:39:55	0	0.00109295	\N	Income	Coinbase	EUR	DAI	773	\N	\N	\N
885	\N	2021-12-28 00:06:41	0	0.00109301	\N	Income	Coinbase	EUR	DAI	774	\N	\N	\N
886	\N	2021-12-28 16:17:23	0	0.0010931	\N	Income	Coinbase	EUR	DAI	775	\N	\N	\N
887	\N	2021-12-29 14:35:32	0	0.00109314	\N	Income	Coinbase	EUR	DAI	776	\N	\N	\N
888	\N	2021-12-30 11:48:14	0	0.00109322	\N	Income	Coinbase	EUR	DAI	777	\N	\N	\N
889	\N	2021-12-31 13:18:28	0	0.00109328	\N	Income	Coinbase	EUR	DAI	778	\N	\N	\N
890	\N	2021-11-29 08:42:05	0	15.38224888	\N	Gift	Coinbase	EUR	AMP	779	\N	\N	\N
891	\N	2021-11-29 08:42:24	0	15.38224888	\N	Gift	Coinbase	EUR	AMP	780	\N	\N	\N
892	\N	2021-11-29 08:43:05	0	15.38224888	\N	Gift	Coinbase	EUR	AMP	781	\N	\N	\N
893	\N	2021-11-29 08:44:08	0	1.01574403	\N	Gift	Coinbase	EUR	GRT	782	\N	\N	\N
894	\N	2021-11-29 08:44:41	0	1.01564087	\N	Gift	Coinbase	EUR	GRT	783	\N	\N	\N
895	\N	2021-11-29 08:45:23	0	1.01564087	\N	Gift	Coinbase	EUR	GRT	784	\N	\N	\N
896	\N	2021-11-29 08:45:42	0	1.01543461	\N	Gift	Coinbase	EUR	GRT	785	\N	\N	\N
897	\N	2021-03-31 08:06:35	-0.00057905	\N	0.00013768	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
898	\N	2021-03-26 20:14:04	0.00029665	\N	0.00227289	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
899	\N	2021-03-26 08:03:58	0.00029877	\N	0.00120304	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
900	\N	2021-03-26 07:40:27	0.00023056	\N	0.00252685	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
901	\N	2021-03-25 08:43:40	0.00028571	\N	0.00251491	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
902	\N	2021-03-24 18:45:53	0.00017524	\N	0.00234897	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
903	\N	2021-03-24 07:05:00	0.00037259	\N	0.00236979	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
904	\N	2021-03-20 20:24:22	-6.462999999999998e-05	\N	0.00359123	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
905	\N	2021-02-22 14:07:45	0.1	\N	3.77	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
906	\N	2021-02-22 03:48:52	0.5299999999999998	\N	74.58	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
907	\N	2021-02-18 19:32:15	-10.520000000000001	16.48	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
908	\N	2021-01-23 17:15:17	1.06	\N	11.24	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
909	\N	2021-01-22 10:29:55	0.85	4.73	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
910	\N	2021-01-22 09:54:59	19.13	\N	641.13	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
911	\N	2021-01-21 15:01:11	12.34	241.33	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
912	\N	2021-01-20 23:05:07	10.26	57.45	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
913	\N	2021-01-20 22:53:13	6.08	\N	71.37	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
914	\N	2021-01-20 20:17:43	6.16	\N	24.77	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
915	\N	2021-01-20 20:02:05	6.18	\N	35.46	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
916	\N	2021-01-20 19:38:53	6.07	16.05	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
917	\N	2021-01-20 17:53:13	2.99	\N	29.08	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
918	\N	2021-01-20 15:30:16	3.41	28.96	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
919	\N	2021-01-20 14:29:09	3.67	\N	29.6	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
920	\N	2021-01-20 13:17:36	6.91	22.26	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
921	\N	2021-01-20 12:00:52	3.65	\N	22.8	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
922	\N	2021-01-20 11:15:28	3.31	43.08	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
923	\N	2021-01-20 09:19:32	9.36	37.66	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
924	\N	2021-01-20 07:01:59	3.03	6.79	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
925	\N	2021-01-19 23:33:46	12.2	\N	11.97	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
926	\N	2021-01-19 23:28:31	7.16	\N	56.5	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
927	\N	2021-01-18 16:04:19	3.889e-05	\N	0.00039315	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
928	\N	2021-01-18 15:14:28	6.1e-06	2.363e-05	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
929	\N	2021-01-18 03:20:16	0.85	\N	159.18	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
930	\N	2021-01-17 21:01:47	1.32	25.57	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
931	\N	2021-01-17 17:47:38	3.99	0.54	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
932	\N	2021-01-17 17:28:04	1.3800000000000003	\N	24.4	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
933	\N	2021-01-15 16:11:39	0.53	\N	9.34	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
934	\N	2021-01-15 16:06:22	0.54	\N	6.13	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
935	\N	2021-01-15 16:02:33	5.3	\N	6.84	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
936	\N	2021-01-15 15:57:39	2.34	107.5	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
937	\N	2021-01-15 13:03:29	3.22	\N	33.67	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
938	\N	2021-01-15 08:12:26	3.59	\N	12.44	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
939	\N	2021-01-15 08:07:12	4.64	\N	41.39	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
940	\N	2021-01-14 17:51:42	2.55	\N	11.52	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
941	\N	2021-01-14 16:55:42	2.62	\N	6.48	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
942	\N	2021-01-14 16:41:58	2.91	\N	17.93	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
943	\N	2021-01-14 16:05:23	2.3900000000000006	38.57	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
944	\N	2021-01-14 15:55:03	4.61	\N	6.86	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
945	\N	2021-01-14 15:48:41	4.6	9.81	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
946	\N	2021-01-14 13:26:13	5.52	\N	33.31	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
947	\N	2021-01-14 13:04:00	9	\N	123.37	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
948	\N	2021-01-14 11:45:03	7.09	\N	17.5	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
949	\N	2021-01-14 11:32:19	2.03	\N	35.48	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
950	\N	2021-01-14 10:45:40	5.33	\N	5.83	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
951	\N	2021-01-14 10:23:27	5.88	\N	37.85	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
952	\N	2021-01-14 09:59:12	5.13	49.99	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
953	\N	2021-01-13 23:26:13	7.26	109.61	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
954	\N	2021-01-13 22:33:48	7.32	\N	5.97	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
955	\N	2021-01-13 21:33:23	7.89	\N	20.41	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
956	\N	2021-01-13 21:15:39	8.66	\N	27.72	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
957	\N	2021-01-13 21:03:31	7.15	\N	28.65	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
958	\N	2021-01-13 20:47:57	7.76	\N	28.52	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
959	\N	2021-01-13 20:39:20	8.19	\N	25.24	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
960	\N	2021-01-13 20:21:28	5.84	100.78	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
961	\N	2021-01-13 14:15:52	6.21	\N	2.3	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
962	\N	2021-01-13 13:07:26	6.29	\N	4.13	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
963	\N	2021-01-13 12:52:10	7.08	\N	36.52	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
964	\N	2021-01-13 12:41:21	8.36	\N	61.27	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
965	\N	2021-01-13 12:02:47	8.09	9.64	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
966	\N	2021-01-13 11:51:10	9.24	\N	53.17	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
967	\N	2021-01-13 11:24:19	1.3199999999999998	\N	19.57	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
968	\N	2021-01-12 15:01:59	1.16	40.62	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
969	\N	2021-01-11 22:38:50	1.78	1.32	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
970	\N	2021-01-11 22:17:45	1.37	\N	51.48	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
971	\N	2021-01-11 16:00:55	2.27	49	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
972	\N	2021-01-11 14:47:14	2.56	\N	25.64	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
973	\N	2021-01-11 13:59:15	6.13	10.96	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
974	\N	2021-01-11 12:08:07	12.32	63.07	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
975	\N	2021-01-10 23:10:45	2.41	24.8	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
976	\N	2021-01-10 22:47:18	2.43	\N	0.25	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
977	\N	2021-01-10 22:08:02	1.81	\N	33.25	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
978	\N	2021-01-10 21:00:24	2.31	\N	25.07	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
979	\N	2021-01-10 20:47:37	6.28	\N	27.07	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
980	\N	2021-01-10 20:31:08	5.03	71.69	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
981	\N	2021-01-10 20:18:55	6.13	\N	7.77	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
982	\N	2021-01-10 20:12:24	1.71	\N	4.47	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
983	\N	2021-01-10 19:54:40	0.7	0.56	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
984	\N	2021-01-10 08:47:30	-2.9699999999999998	\N	24.3	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
985	\N	2021-01-09 23:40:08	0.4	\N	0.13	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
986	\N	2021-01-08 14:01:48	1.069e-05	\N	8.217e-05	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
987	\N	2021-01-08 13:55:17	1.811e-05	\N	5.975e-05	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
988	\N	2021-01-08 13:53:45	1.411e-05	3.281e-05	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
989	\N	2021-01-08 09:36:31	0.00027613	\N	0.00212577	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
990	\N	2021-05-17 04:30:51	0.00065812	\N	0.00957405	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
991	\N	2021-05-17 04:20:40	0.00051775	\N	0.1641751	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
992	\N	2021-05-14 09:11:06	0.00140658	\N	0.00369826	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
993	\N	2021-05-14 08:47:33	0.0014326	\N	0.00618417	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
994	\N	2021-05-13 23:54:36	0.00076813	\N	0.00647946	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
995	\N	2021-05-13 19:56:48	0.00144357	0.00096016	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
996	\N	2021-05-13 19:09:22	0.0014909	\N	0.00799882	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
997	\N	2021-05-13 19:01:04	0.00150157	\N	0.00101402	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
998	\N	2021-05-13 18:19:51	0.00146431	0.00674166	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
999	\N	2021-05-13 16:02:05	0.0015749100000000001	\N	0.00056376	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1000	\N	2021-05-13 15:03:53	0.00148892	\N	0.01319431	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1001	\N	2021-05-13 07:40:41	0.00146984	\N	0.00714322	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1002	\N	2021-05-12 22:07:33	0.00137651	\N	0.00711169	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1003	\N	2021-05-12 12:09:27	0.00132294	0.00116333	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
1004	\N	2021-05-04 16:07:11	0.00139169	\N	0.01156753	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1005	\N	2021-05-04 15:44:19	0.0013784	\N	0.00544843	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1006	\N	2021-05-04 15:41:24	0.00138012	\N	0.00506456	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1007	\N	2021-05-04 15:27:11	0.00137634	0.00937435	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
1008	\N	2021-05-04 14:38:06	-0.00026617000000000004	\N	0.05762225	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1009	\N	2021-05-03 00:05:32	0.0008168699999999999	\N	0.00252788	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1010	\N	2021-05-02 22:14:16	0.00030188000000000003	0.00327573	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
1011	\N	2021-04-28 19:29:57	0.00136569	\N	0.00414	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1012	\N	2021-04-28 17:44:28	0.0014587699999999999	\N	0.00514088	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1013	\N	2021-04-28 11:26:26	0.0013677	\N	0.00466094	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1014	\N	2021-04-28 10:09:43	0.0014656299999999999	0.00436998	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
1015	\N	2021-04-27 18:33:14	0.00136537	\N	0.00366041	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1016	\N	2021-04-27 17:12:12	0.00045704	\N	0.00348139	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1017	\N	2021-04-27 10:50:27	0.00137139	\N	0.00315988	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1018	\N	2021-04-27 10:05:27	0.00137336	\N	0.00470118	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1019	\N	2021-04-26 21:19:30	0.0014128	0.00986201	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
1020	\N	2021-04-26 17:20:22	0.00148615	\N	0.0126082	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1021	\N	2021-04-26 12:18:14	0.00131613	0.01014063	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
1022	\N	2021-04-26 05:31:47	0.00143326	\N	0.00620815	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1023	\N	2021-04-26 00:00:42	0.0014413599999999998	\N	0.00562078	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1024	\N	2021-04-25 23:28:42	0.00203674	0.02735409	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
1025	\N	2021-04-25 15:08:40	0.00148784	\N	0.00410486	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1026	\N	2021-04-25 14:37:09	0.00147117	\N	0.0045218	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1027	\N	2021-04-25 14:29:58	0.00150118	\N	0.00485639	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1028	\N	2021-04-25 13:10:38	0.0013316	\N	0.00165172	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1029	\N	2021-04-25 12:53:29	0.00133611	\N	0.00377356	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1030	\N	2021-04-25 12:49:05	0.00135404	\N	0.00473396	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1031	\N	2021-04-24 07:07:04	0.00196334	0.01094765	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
1032	\N	2021-04-23 23:44:34	0.00131987	\N	0.0093223	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1033	\N	2021-04-23 18:45:41	0.00133686	\N	0.00055669	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1034	\N	2021-04-23 18:36:47	0.00136287	\N	0.00982733	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1035	\N	2021-04-23 14:27:57	0.00137839	\N	0.003945	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1036	\N	2021-04-23 13:30:11	0.00135555	0.00541499	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
1037	\N	2021-04-23 12:54:09	0.00068954	\N	0.00287853	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1038	\N	2021-04-23 11:13:08	0.00069123	\N	0.00378432	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1039	\N	2021-04-22 18:28:23	0.00073035	0.0107836	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
1040	\N	2021-04-22 12:35:11	0.00068151	\N	0.00367656	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1041	\N	2021-04-22 11:18:28	0.0006925	\N	0.00349224	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1042	\N	2021-04-22 09:58:04	0.0007405199999999999	0.0018597	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
1043	\N	2021-04-22 06:18:53	0.00069391	\N	0.0032574	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1044	\N	2021-04-21 22:27:50	0.00069466	0.00403924	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
1045	\N	2021-04-21 18:49:15	0.00065683	0.00482907	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
1046	\N	2021-04-21 11:51:45	0.00069071	\N	0.00354049	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1047	\N	2021-04-21 11:26:19	0.00069714	\N	0.00403803	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1048	\N	2021-04-21 08:03:53	0.00065628	\N	0.00406698	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1049	\N	2021-04-20 22:50:03	0.00069729	0.0012298	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
1050	\N	2021-04-20 18:06:01	0.00069953	\N	0.00138448	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1051	\N	2021-04-20 16:53:49	0.00069748	\N	0.00402774	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1052	\N	2021-04-20 15:57:13	0.0006997	0.00039273	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
1053	\N	2021-04-09 06:57:06	0.00026074	\N	0.00102977	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1054	\N	2021-04-08 20:18:18	0.00051798	\N	0.00301602	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1055	\N	2021-04-08 18:30:30	0.00058199	\N	0.00193954	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1056	\N	2021-04-08 12:47:24	0.0001819	\N	0.00387819	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1057	\N	2021-04-07 23:58:35	0.00053527	\N	0.00221898	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1058	\N	2021-04-07 19:03:57	0.00051136	0.00540059	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
1059	\N	2021-04-06 14:02:19	-0.00024675	\N	0.00261592	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1060	\N	2021-04-05 14:38:31	0.0016995399999999998	\N	0.00579991	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1061	\N	2021-04-02 16:21:36	-9.495999999999984e-05	\N	0.00493006	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1062	\N	2021-04-02 02:26:04	0.00012328000000000003	0.00376178	\N	Deposit	Phemex	BTC	BTC	\N	\N	\N	\N
1063	\N	2021-04-01 18:19:33	-0.0008031000000000002	\N	0.00334861	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1064	\N	2021-09-07 15:12:37	3.21	\N	39.09	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1065	\N	2021-09-07 14:15:54	1.51	\N	15.16	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1066	\N	2021-09-07 08:31:02	-5.34	\N	26.95	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1067	\N	2021-09-02 14:07:04	4.33	\N	21.71	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1068	\N	2021-08-30 20:35:28	0.4999999999999999	28.36	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1069	\N	2021-08-27 00:16:27	2.37	\N	22.06	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1070	\N	2021-08-26 15:49:09	3.52	\N	27.11	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1071	\N	2021-08-26 10:00:25	4.64	\N	38.53	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1072	\N	2021-08-26 03:07:49	2.29	39.91	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1073	\N	2021-08-25 08:39:37	0.69	\N	43.08	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1074	\N	2021-08-18 07:45:59	3.53	\N	0.62	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1075	\N	2021-08-17 12:09:19	2.19	38.16	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1076	\N	2021-08-15 22:50:05	2.06	56.05	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1077	\N	2021-08-15 17:04:58	1.6400000000000001	\N	28.12	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1078	\N	2021-08-14 09:56:36	2.1	\N	26.22	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1079	\N	2021-08-12 22:07:41	3.49	2.32	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1080	\N	2021-08-12 14:24:00	4.58	\N	42.6	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1081	\N	2021-08-12 12:28:57	4.32	\N	49.36	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1082	\N	2021-08-10 13:41:18	3.61	52.01	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1083	\N	2021-08-10 10:32:37	4.32	\N	50.39	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1084	\N	2021-08-09 09:20:06	2.57	52.85	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1085	\N	2021-08-06 16:10:14	2.03	61.66	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1086	\N	2021-08-06 10:08:42	0.61	\N	23.51	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1087	\N	2021-08-05 14:05:22	1.66	29.38	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1088	\N	2021-08-05 12:44:57	1.72	\N	24.61	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1089	\N	2021-08-04 13:00:06	0.81	\N	26	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1090	\N	2021-08-03 12:15:25	0.99	\N	8.26	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1091	\N	2021-08-03 11:37:26	0.94	\N	1.79	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1092	\N	2021-08-03 07:17:41	1.31	\N	9.89	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1093	\N	2021-08-01 19:33:51	1.01	10.21	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1094	\N	2021-07-30 20:09:38	0.71	8.3	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1095	\N	2021-07-28 11:28:35	0.6	6.13	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1096	\N	2021-07-28 08:41:24	0.77	\N	6.31	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1097	\N	2021-07-27 11:48:53	0.99	\N	7.6	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1098	\N	2021-07-27 08:15:23	1.36	\N	9.93	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1099	\N	2021-07-26 13:13:31	0.99	10	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1100	\N	2021-07-23 16:06:44	0.84	8.37	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1101	\N	2021-07-22 12:57:23	0.99	\N	7.94	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1102	\N	2021-07-22 09:36:28	1.26	\N	11.01	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1103	\N	2021-07-22 07:08:28	1.71	\N	14.51	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1104	\N	2021-07-21 10:08:23	1.32	14.07	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1105	\N	2021-07-21 00:36:29	1.03	10.29	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1106	\N	2021-07-20 20:13:03	1.3	\N	10	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1107	\N	2021-07-20 15:13:08	1.7	\N	14.92	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1108	\N	2021-07-19 10:45:52	1.4100000000000001	13.71	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1109	\N	2021-07-16 22:34:27	1.02	9.33	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1110	\N	2021-07-16 16:33:57	1.28	\N	7.76	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1111	\N	2021-07-16 08:45:25	1.03	9.9	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1112	\N	2021-07-15 10:44:48	0.74	8.97	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1113	\N	2021-07-14 09:04:42	0.64	\N	7.42	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1114	\N	2021-07-13 19:29:08	0.71	9.07	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1115	\N	2021-07-13 16:57:38	0.92	\N	6.5	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1116	\N	2021-07-13 15:25:38	1.1	\N	8.13	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1117	\N	2021-07-13 13:56:24	1.25	\N	9.18	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1118	\N	2021-07-13 12:36:11	1.09	10.31	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1119	\N	2021-07-13 10:00:30	1.37	\N	8.84	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1120	\N	2021-07-13 06:37:47	1.69	\N	12.58	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1121	\N	2021-07-12 11:58:57	1.39	11.24	\N	Deposit	Phemex	USD	USD	\N	\N	\N	\N
1122	\N	2021-07-12 08:20:23	1.65	\N	13.87	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1123	\N	2021-07-09 13:07:44	1.68	\N	19.23	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1124	\N	2021-07-09 09:30:29	2.04	\N	17.1	Withdraw	Phemex	USD	\N	\N	USD	\N	\N
1125	\N	2021-12-29 23:20:50	9.027e-05	\N	0.00148953	Withdraw	Phemex	BTC	\N	\N	BTC	\N	\N
1126	\N	2021-12-29 12:12:42	0.00084234	\N	0.03544794	Withdraw	Phemex	ETH	\N	\N	ETH	\N	\N
1127	\N	2021-12-28 14:39:10	0.00147053	\N	0.0151292	Withdraw	Phemex	ETH	\N	\N	ETH	\N	\N
1128	\N	2021-12-28 00:07:18	0.0012279399999999998	0.01108262	\N	Deposit	Phemex	ETH	ETH	\N	\N	\N	\N
1129	\N	2021-12-27 17:37:31	0.00140534	\N	0.00938364	Withdraw	Phemex	ETH	\N	\N	ETH	\N	\N
1130	\N	2021-12-26 00:43:43	0.00129651	\N	0.00019636	Withdraw	Phemex	ETH	\N	\N	ETH	\N	\N
1131	\N	2021-12-25 17:25:42	0.00145723	\N	0.00450366	Withdraw	Phemex	ETH	\N	\N	ETH	\N	\N
1132	\N	2021-12-23 08:06:45	0.00123884	0.01623063	\N	Deposit	Phemex	ETH	ETH	\N	\N	\N	\N
1133	\N	2021-12-22 16:10:42	0.00074235	\N	0.01033052	Withdraw	Phemex	ETH	\N	\N	ETH	\N	\N
1134	\N	2021-12-22 09:37:37	0.0006171799999999999	\N	0.00052035	Withdraw	Phemex	ETH	\N	\N	ETH	\N	\N
1135	\N	2021-12-21 19:57:21	0.00073967	\N	0.00053136	Withdraw	Phemex	ETH	\N	\N	ETH	\N	\N
1136	\N	2021-12-21 12:32:52	0.00083322	\N	0.00805122	Withdraw	Phemex	ETH	\N	\N	ETH	\N	\N
1137	\N	2021-12-20 21:34:19	0.00060051	0.01237905	\N	Deposit	Phemex	ETH	ETH	\N	\N	\N	\N
1138	\N	2021-12-17 07:00:24	0.0009539899999999999	0.01419332	\N	Deposit	Phemex	ETH	ETH	\N	\N	\N	\N
1139	\N	2021-12-16 20:27:51	0.00089179	0.00134763	\N	Deposit	Phemex	ETH	ETH	\N	\N	\N	\N
1140	\N	2021-12-16 13:29:43	0.00083147	0.00022002	\N	Deposit	Phemex	ETH	ETH	\N	\N	\N	\N
1141	\N	2021-12-16 00:17:27	0.00066179	\N	0.0005746	Withdraw	Phemex	ETH	\N	\N	ETH	\N	\N
1142	\N	2021-12-15 17:12:12	0.00061408	0.00960428	\N	Deposit	Phemex	ETH	ETH	\N	\N	\N	\N
1143	\N	2021-12-15 08:31:22	0.00026219000000000004	0.0060209	\N	Deposit	Phemex	ETH	ETH	\N	\N	\N	\N
1144	\N	2021-12-12 17:03:16	0.0005397599999999999	\N	0.00532869	Withdraw	Phemex	ETH	\N	\N	ETH	\N	\N
1145	\N	2021-12-12 04:47:10	0.00039695	\N	0.00045269	Withdraw	Phemex	ETH	\N	\N	ETH	\N	\N
1146	\N	2021-12-10 23:38:01	0.00036994	\N	0.00551439	Withdraw	Phemex	ETH	\N	\N	ETH	\N	\N
1147	\N	2021-12-10 19:38:38	0.00039561	\N	0.00268225	Withdraw	Phemex	ETH	\N	\N	ETH	\N	\N
1148	\N	2021-12-10 18:16:24	0.00041203	0.00139092	\N	Deposit	Phemex	ETH	ETH	\N	\N	\N	\N
1149	\N	2021-12-10 14:49:17	0.00037103	0.00086537	\N	Deposit	Phemex	ETH	ETH	\N	\N	\N	\N
1150	\N	2021-12-07 20:34:04	0.00069142	\N	0.00613407	Withdraw	Phemex	ETH	\N	\N	ETH	\N	\N
1151	\N	2021-12-06 19:10:05	0.00072416	\N	0.0042789	Withdraw	Phemex	ETH	\N	\N	ETH	\N	\N
1152	\N	2021-12-06 17:32:23	0.00037758	0.00764316	\N	Deposit	Phemex	ETH	ETH	\N	\N	\N	\N
1153	\N	2021-12-06 11:45:44	0.00033718	\N	0.00020407	Withdraw	Phemex	ETH	\N	\N	ETH	\N	\N
1154	\N	2021-12-03 16:00:18	0.00029509	\N	0.00533914	Withdraw	Phemex	ETH	\N	\N	ETH	\N	\N
1155	\N	2021-12-02 13:28:54	0.00058667	0.0073604	\N	Deposit	Phemex	ETH	ETH	\N	\N	\N	\N
1156	\N	2021-12-01 20:57:52	0.00032048	0.00616412	\N	Deposit	Phemex	ETH	ETH	\N	\N	\N	\N
1157	\N	2021-01-19 17:32:31.099	10.17358704	0.06627744	2044.89099504	Trade	Coinbase Pro	EUR	BTC	786	EUR	\N	\N
1158	\N	2021-01-19 17:32:31.099	0.93967174	0.00612164	188.87401974	Trade	Coinbase Pro	EUR	BTC	787	EUR	\N	\N
1159	\N	2021-01-19 17:32:31.099	1.919079375	0.0125	385.734954375	Trade	Coinbase Pro	EUR	BTC	788	EUR	\N	\N
1160	\N	2021-01-19 17:32:31.099	7.875906885	0.0513	1583.057283885	Trade	Coinbase Pro	EUR	BTC	789	EUR	\N	\N
1161	\N	2021-01-19 17:32:31.099	0.1709104309	0.0011132	34.3529966109	Trade	Coinbase Pro	EUR	BTC	790	EUR	\N	\N
1162	\N	2021-01-19 17:32:31.099	0.94037615	0.006125	189.01560615	Trade	Coinbase Pro	EUR	BTC	791	EUR	\N	\N
1163	\N	2021-01-19 17:32:31.099	4.284646982141	0.02790482	861.214043410341	Trade	Coinbase Pro	EUR	BTC	792	EUR	\N	\N
1169	\N	2021-01-19 17:31:17	0	0.33622	12485.22	Trade	Phemex	BTC	BTC	804	USDT	803	\N
1170	\N	2021-01-03 06:40:23	0	6613.2	0.194475	Trade	Phemex	USDT	USDT	806	BTC	805	\N
1171	\N	2021-01-03 06:40:23	0	340.1	0.01	Trade	Phemex	USDT	USDT	808	BTC	807	\N
1172	\N	2021-01-02 23:03:39	0	2392.56	0.075611	Trade	Phemex	USDT	USDT	810	BTC	809	\N
1173	\N	2021-01-02 23:03:39	0	4077.58	0.128862	Trade	Phemex	USDT	USDT	812	BTC	811	\N
1243	\N	2021-01-31 19:48:49	0	\N	0.00264682	Withdraw	ByBit	BTC	\N	\N	BTC	\N	\N
1244	\N	2021-01-31 19:06:10	0	2.74e-05	\N	Deposit	ByBit	BTC	BTC	\N	\N	\N	\N
1245	\N	2021-01-31 18:53:27	0	\N	6.01e-05	Withdraw	ByBit	BTC	\N	\N	BTC	\N	\N
1246	\N	2021-01-29 18:46:53	0	\N	0.05180129	Withdraw	ByBit	BTC	\N	\N	BTC	\N	\N
1247	\N	2021-01-29 14:02:44	0	0.00316862	\N	Deposit	ByBit	BTC	BTC	\N	\N	\N	\N
1248	\N	2021-01-27 13:47:39	0	0.00490021	\N	Deposit	ByBit	BTC	BTC	\N	\N	\N	\N
1249	\N	2021-01-26 12:26:35	0	\N	0.04335273	Withdraw	ByBit	BTC	\N	\N	BTC	\N	\N
1250	\N	2021-01-25 00:48:40	0	\N	0.02582955	Withdraw	ByBit	BTC	\N	\N	BTC	\N	\N
1251	\N	2021-01-23 15:13:36	0	\N	0.00418851	Withdraw	ByBit	BTC	\N	\N	BTC	\N	\N
1252	\N	2021-01-23 14:00:11	0	0.00013049	\N	Deposit	ByBit	BTC	BTC	\N	\N	\N	\N
1253	\N	2021-01-23 13:44:35	0	\N	0.00133812	Withdraw	ByBit	BTC	\N	\N	BTC	\N	\N
1254	\N	2021-01-23 13:40:58	0	\N	0.0057971	Withdraw	ByBit	BTC	\N	\N	BTC	\N	\N
1255	\N	2021-01-21 15:00:58	0	0.08161713	\N	Deposit	ByBit	BTC	BTC	\N	\N	\N	\N
1256	\N	2021-02-16 12:01:02	0	\N	0.00064027	Withdraw	ByBit	BTC	\N	\N	BTC	\N	\N
1257	\N	2021-02-16 07:56:21	0	\N	0.00115761	Withdraw	ByBit	BTC	\N	\N	BTC	\N	\N
1258	\N	2021-02-15 16:25:09	0	\N	8.622e-05	Withdraw	ByBit	BTC	\N	\N	BTC	\N	\N
1259	\N	2021-02-15 16:18:04	0	\N	0.00023446	Withdraw	ByBit	BTC	\N	\N	BTC	\N	\N
1260	\N	2021-02-12 14:29:31	0	\N	6.327e-05	Withdraw	ByBit	BTC	\N	\N	BTC	\N	\N
1261	\N	2021-02-11 13:11:23	0	0.00092536	\N	Deposit	ByBit	BTC	BTC	\N	\N	\N	\N
1262	\N	2021-02-10 15:27:37	0	\N	0.00010809	Withdraw	ByBit	BTC	\N	\N	BTC	\N	\N
1263	\N	2021-02-03 21:28:49	0	\N	0.00300713	Withdraw	ByBit	BTC	\N	\N	BTC	\N	\N
1264	\N	2021-02-03 14:36:27	0	0.00177086	\N	Deposit	ByBit	BTC	BTC	\N	\N	\N	\N
1265	\N	2021-02-03 12:14:51	0	\N	0.00299881	Withdraw	ByBit	BTC	\N	\N	BTC	\N	\N
1266	\N	2021-02-03 08:31:49	0	0.01380193	\N	Deposit	ByBit	BTC	BTC	\N	\N	\N	\N
1267	\N	2021-02-02 15:28:49	0	\N	0.00503784	Withdraw	ByBit	BTC	\N	\N	BTC	\N	\N
1268	\N	2021-02-02 15:17:48	0	\N	0.00393863	Withdraw	ByBit	BTC	\N	\N	BTC	\N	\N
1269	\N	2021-02-01 09:32:49	0	8.74e-06	\N	Deposit	ByBit	BTC	BTC	\N	\N	\N	\N
1270	\N	2021-09-22 17:01:15	0	\N	37.72763501	Withdraw	ByBit	ETH	\N	\N	ETH	\N	\N
1271	\N	2021-09-22 05:09:13	0	\N	10.84852726	Withdraw	ByBit	ETH	\N	\N	ETH	\N	\N
1272	\N	2021-09-22 04:31:45	0	\N	15.37831575	Withdraw	ByBit	ETH	\N	\N	ETH	\N	\N
1273	\N	2021-09-19 11:47:51	0	18.412698	\N	Deposit	ByBit	ETH	ETH	\N	\N	\N	\N
1274	\N	2021-09-15 15:05:47	0	\N	13.81329365	Withdraw	ByBit	ETH	\N	\N	ETH	\N	\N
1275	\N	2021-09-14 22:53:14	0	\N	20.111766	Withdraw	ByBit	ETH	\N	\N	ETH	\N	\N
1298	\N	2021-01-08 20:35:42	\N	400	0.010091352	Trade	Phemex	\N	USD	852	BTC	851	\N
1299	\N	2021-01-18 10:58:57	\N	0.000317372	11.51	Trade	Phemex	\N	BTC	854	USD	853	\N
1300	\N	2021-01-19 14:24:33	\N	500.39	0.013494563	Trade	Phemex	\N	USD	856	BTC	855	\N
1301	\N	2021-02-10 08:14:32	\N	38.23	0.000817023	Trade	Phemex	\N	USD	858	BTC	857	\N
1302	\N	2021-07-09 07:11:50	\N	98.38	0.002995372	Trade	Phemex	\N	USD	860	BTC	859	\N
1303	\N	2021-08-04 16:34:23	\N	117.87	0.002991981	Trade	Phemex	\N	USD	862	BTC	861	\N
1304	\N	2021-01-04 20:53:40	\N	0.00190716	\N	Gift	Phemex	\N	BTC	863	\N	\N	\N
1305	\N	2021-01-04 20:54:56	\N	0.00031806	\N	Gift	Phemex	\N	BTC	864	\N	\N	\N
1306	\N	2021-03-17 12:03:17	\N	0.00728858	\N	Gift	Phemex	\N	BTC	865	\N	\N	\N
1307	\N	2021-04-08 04:10:16	\N	0.00017684	\N	Gift	Phemex	\N	BTC	866	\N	\N	\N
1308	\N	2021-04-09 02:36:37	\N	0.00017216	\N	Gift	Phemex	\N	BTC	867	\N	\N	\N
\.


--
-- Data for Name: transaction_import; Type: TABLE DATA; Schema: public; Owner: appuser
--

COPY public.transaction_import (id, comment, date_time, exchange, fee, fee_currency, in_currency, in_value, out_currency, out_value, type, in_fiat_exchange_id, out_fiat_exchange_id) FROM stdin;
\.


--
-- Name: account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: appuser
--

SELECT pg_catalog.setval('public.account_id_seq', 1, false);


--
-- Name: customer_seq; Type: SEQUENCE SET; Schema: public; Owner: appuser
--

SELECT pg_catalog.setval('public.customer_seq', 1, false);


--
-- Name: fiat_exchange_rate_id_seq; Type: SEQUENCE SET; Schema: public; Owner: appuser
--

SELECT pg_catalog.setval('public.fiat_exchange_rate_id_seq', 1, true);


--
-- Name: fiat_exchange_rate_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: appuser
--

SELECT pg_catalog.setval('public.fiat_exchange_rate_id_seq1', 1, false);


--
-- Name: gain_id_seq; Type: SEQUENCE SET; Schema: public; Owner: appuser
--

SELECT pg_catalog.setval('public.gain_id_seq', 1, false);


--
-- Name: hold_id_seq; Type: SEQUENCE SET; Schema: public; Owner: appuser
--

SELECT pg_catalog.setval('public.hold_id_seq', 1, false);


--
-- Name: income_id_seq; Type: SEQUENCE SET; Schema: public; Owner: appuser
--

SELECT pg_catalog.setval('public.income_id_seq', 1, false);


--
-- Name: trades_id_seq; Type: SEQUENCE SET; Schema: public; Owner: appuser
--

SELECT pg_catalog.setval('public.trades_id_seq', 1, false);


--
-- Name: trading_images_id_seq; Type: SEQUENCE SET; Schema: public; Owner: appuser
--

SELECT pg_catalog.setval('public.trading_images_id_seq', 1, false);


--
-- Name: transaction_id_seq; Type: SEQUENCE SET; Schema: public; Owner: appuser
--

SELECT pg_catalog.setval('public.transaction_id_seq', 1, false);


--
-- Name: transaction_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: appuser
--

SELECT pg_catalog.setval('public.transaction_id_seq1', 1, false);


--
-- Name: transaction_import_id_seq; Type: SEQUENCE SET; Schema: public; Owner: appuser
--

SELECT pg_catalog.setval('public.transaction_import_id_seq', 1, false);


--
-- Name: account account_pkey; Type: CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pkey PRIMARY KEY (id);


--
-- Name: currency currency_pkey; Type: CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.currency
    ADD CONSTRAINT currency_pkey PRIMARY KEY (ticker);


--
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- Name: fiat_exchange_rate fiat_exchange_rate_pkey; Type: CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.fiat_exchange_rate
    ADD CONSTRAINT fiat_exchange_rate_pkey PRIMARY KEY (id);


--
-- Name: gain gain_pkey; Type: CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.gain
    ADD CONSTRAINT gain_pkey PRIMARY KEY (id);


--
-- Name: hold hold_pkey; Type: CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.hold
    ADD CONSTRAINT hold_pkey PRIMARY KEY (id);


--
-- Name: income income_pkey; Type: CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.income
    ADD CONSTRAINT income_pkey PRIMARY KEY (id);


--
-- Name: location location_pkey; Type: CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.location
    ADD CONSTRAINT location_pkey PRIMARY KEY (name);


--
-- Name: trades_images trades_images_pkey; Type: CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.trades_images
    ADD CONSTRAINT trades_images_pkey PRIMARY KEY (trade_id, images_id);


--
-- Name: trades trades_pkey; Type: CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.trades
    ADD CONSTRAINT trades_pkey PRIMARY KEY (id);


--
-- Name: trading_images trading_images_pkey; Type: CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.trading_images
    ADD CONSTRAINT trading_images_pkey PRIMARY KEY (id);


--
-- Name: transaction_import transaction_import_pkey; Type: CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.transaction_import
    ADD CONSTRAINT transaction_import_pkey PRIMARY KEY (id);


--
-- Name: transaction transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_pkey PRIMARY KEY (id);


--
-- Name: trades_images uk_j5snol0a95bbig9ivvgj17mhs; Type: CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.trades_images
    ADD CONSTRAINT uk_j5snol0a95bbig9ivvgj17mhs UNIQUE (images_id);


--
-- Name: trades fk2ltj9hc5215025gw5ity6rqsc; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.trades
    ADD CONSTRAINT fk2ltj9hc5215025gw5ity6rqsc FOREIGN KEY (buy_fiat_exchange_id) REFERENCES public.fiat_exchange_rate(id);


--
-- Name: fiat_exchange_rate fk2u71q90br5hmueqdwukqfjliq; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.fiat_exchange_rate
    ADD CONSTRAINT fk2u71q90br5hmueqdwukqfjliq FOREIGN KEY (exchange_name) REFERENCES public.location(name);


--
-- Name: income fk3dc250d3a8bsu0tbn28yde7ll; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.income
    ADD CONSTRAINT fk3dc250d3a8bsu0tbn28yde7ll FOREIGN KEY (currency_ticker) REFERENCES public.currency(ticker);


--
-- Name: trades_images fk3q98varqrb6jhgd98xlnd7ieh; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.trades_images
    ADD CONSTRAINT fk3q98varqrb6jhgd98xlnd7ieh FOREIGN KEY (images_id) REFERENCES public.trading_images(id);


--
-- Name: transaction fk7cxrtevewqk703qq69py4byrd; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT fk7cxrtevewqk703qq69py4byrd FOREIGN KEY (out_fiat_exchange_id) REFERENCES public.fiat_exchange_rate(id);


--
-- Name: gain fk7iiksivpsnp126cvr0as312uc; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.gain
    ADD CONSTRAINT fk7iiksivpsnp126cvr0as312uc FOREIGN KEY (sell_at_name) REFERENCES public.location(name);


--
-- Name: hold fk80gjhowxjmj859mp7khi61dfp; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.hold
    ADD CONSTRAINT fk80gjhowxjmj859mp7khi61dfp FOREIGN KEY (in_currency_ticker) REFERENCES public.currency(ticker);


--
-- Name: transaction fk8rxs3qghlix1gjb2fcfkufs78; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT fk8rxs3qghlix1gjb2fcfkufs78 FOREIGN KEY (out_currency_ticker) REFERENCES public.currency(ticker);


--
-- Name: transaction fk8w1vw5fi6x8c0pb2satuqvja4; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT fk8w1vw5fi6x8c0pb2satuqvja4 FOREIGN KEY (fee_currency_ticker) REFERENCES public.currency(ticker);


--
-- Name: transaction fka1e426auicaa03969p5pojav1; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT fka1e426auicaa03969p5pojav1 FOREIGN KEY (in_fiat_exchange_id) REFERENCES public.fiat_exchange_rate(id);


--
-- Name: account fka7q89n6louek4xj0fguh9udk7; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT fka7q89n6louek4xj0fguh9udk7 FOREIGN KEY (reference_currency_ticker) REFERENCES public.currency(ticker);


--
-- Name: fiat_exchange_rate fkbj9h75kc8jrrt8y5ak1x5wle5; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.fiat_exchange_rate
    ADD CONSTRAINT fkbj9h75kc8jrrt8y5ak1x5wle5 FOREIGN KEY (fiat_currency_ticker) REFERENCES public.currency(ticker);


--
-- Name: trades fkcmh2h3shyel220amjwxlm5b2n; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.trades
    ADD CONSTRAINT fkcmh2h3shyel220amjwxlm5b2n FOREIGN KEY (exchange_name) REFERENCES public.location(name);


--
-- Name: transaction fkdxquf2d3n6v1jf05uirxftbps; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT fkdxquf2d3n6v1jf05uirxftbps FOREIGN KEY (exchange_name) REFERENCES public.location(name);


--
-- Name: trades fkerse65ehxv85nex0qufxa526x; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.trades
    ADD CONSTRAINT fkerse65ehxv85nex0qufxa526x FOREIGN KEY (sell_currency_ticker) REFERENCES public.currency(ticker);


--
-- Name: trades fkfrpoorimn8k35jm55wrt5s9hu; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.trades
    ADD CONSTRAINT fkfrpoorimn8k35jm55wrt5s9hu FOREIGN KEY (currency_ticker) REFERENCES public.currency(ticker);


--
-- Name: transaction_import fkgkikeg37hchbrjt11c2bg5lxk; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.transaction_import
    ADD CONSTRAINT fkgkikeg37hchbrjt11c2bg5lxk FOREIGN KEY (out_fiat_exchange_id) REFERENCES public.fiat_exchange_rate(id);


--
-- Name: transaction_import fkgpv3oh6v8n8wy5uqhufy73u4x; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.transaction_import
    ADD CONSTRAINT fkgpv3oh6v8n8wy5uqhufy73u4x FOREIGN KEY (in_fiat_exchange_id) REFERENCES public.fiat_exchange_rate(id);


--
-- Name: gain fkh32mt6mj85ef0gpt25lfe0v1h; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.gain
    ADD CONSTRAINT fkh32mt6mj85ef0gpt25lfe0v1h FOREIGN KEY (buy_at_name) REFERENCES public.location(name);


--
-- Name: trades fkifbf40e6toluohgjjksw3vo4k; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.trades
    ADD CONSTRAINT fkifbf40e6toluohgjjksw3vo4k FOREIGN KEY (sell_fiat_exchange_id) REFERENCES public.fiat_exchange_rate(id);


--
-- Name: gain fkkj05dpjinsiomlubx2udg0k5h; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.gain
    ADD CONSTRAINT fkkj05dpjinsiomlubx2udg0k5h FOREIGN KEY (currency_ticker) REFERENCES public.currency(ticker);


--
-- Name: trades fklqe1ws4ftypvuifh9yob3jmir; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.trades
    ADD CONSTRAINT fklqe1ws4ftypvuifh9yob3jmir FOREIGN KEY (buy_currency_ticker) REFERENCES public.currency(ticker);


--
-- Name: transaction fklxbfvnkpty6eyuxsq3np4hyl0; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT fklxbfvnkpty6eyuxsq3np4hyl0 FOREIGN KEY (trade_id) REFERENCES public.trades(id);


--
-- Name: fiat_exchange_rate fknte8obgenluhbkadmgm614y4a; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.fiat_exchange_rate
    ADD CONSTRAINT fknte8obgenluhbkadmgm614y4a FOREIGN KEY (crypto_currency_ticker) REFERENCES public.currency(ticker);


--
-- Name: trades_images fkpjbuw2eew9i407o8udmsdqiu3; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.trades_images
    ADD CONSTRAINT fkpjbuw2eew9i407o8udmsdqiu3 FOREIGN KEY (trade_id) REFERENCES public.trades(id);


--
-- Name: income fkqamp0y4nkyr71uv0e9mht2of9; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.income
    ADD CONSTRAINT fkqamp0y4nkyr71uv0e9mht2of9 FOREIGN KEY (in_at_name) REFERENCES public.location(name);


--
-- Name: income fkqrdm0f8sjiwkxmrpjxyxcgwpk; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.income
    ADD CONSTRAINT fkqrdm0f8sjiwkxmrpjxyxcgwpk FOREIGN KEY (out_at_name) REFERENCES public.location(name);


--
-- Name: hold fkrfvkvpmdw2nhcfb7twx8wlvkn; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.hold
    ADD CONSTRAINT fkrfvkvpmdw2nhcfb7twx8wlvkn FOREIGN KEY (location_name) REFERENCES public.location(name);


--
-- Name: transaction fkrkewmnamcc4do8arx5wvu1djb; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT fkrkewmnamcc4do8arx5wvu1djb FOREIGN KEY (in_currency_ticker) REFERENCES public.currency(ticker);


--
-- Name: trades fks02i9h2fxl5kybng8vyrbv0as; Type: FK CONSTRAINT; Schema: public; Owner: appuser
--

ALTER TABLE ONLY public.trades
    ADD CONSTRAINT fks02i9h2fxl5kybng8vyrbv0as FOREIGN KEY (trade_fiat_exchange_id) REFERENCES public.fiat_exchange_rate(id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: pg_database_owner
--

GRANT ALL ON SCHEMA public TO appuser;


--
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: public; Owner: appuser
--

ALTER DEFAULT PRIVILEGES FOR ROLE appuser IN SCHEMA public GRANT ALL ON TABLES  TO appuser;


--
-- PostgreSQL database dump complete
--

