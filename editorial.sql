--
-- PostgreSQL database dump
--

-- Dumped from database version 12.3
-- Dumped by pg_dump version 12.3

-- Started on 2019-07-16 17:05:03

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

--
-- TOC entry 8 (class 2615 OID 16397)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 209 (class 1255 OID 16732)
-- Name: f_persona_edad(date); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.f_persona_edad(fecha_nacimiento date) RETURNS integer
    LANGUAGE plpgsql IMMUTABLE
    AS $$BEGIN
    RETURN extract( year FROM CURRENT_DATE )
           - extract( year FROM fecha_nacimiento );
END
$$;


ALTER FUNCTION public.f_persona_edad(fecha_nacimiento date) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 203 (class 1259 OID 16528)
-- Name: direccion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.direccion (
    dni character varying(10) NOT NULL,
    numero integer NOT NULL,
    calle character varying(100) NOT NULL
);


ALTER TABLE public.direccion OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 16701)
-- Name: persona; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persona (
    dni character varying(10) NOT NULL,
    nombre character varying(20) NOT NULL,
    apellido character varying(20) NOT NULL,
    fecha_nacimiento date NOT NULL,
    edad integer GENERATED ALWAYS AS (public.f_persona_edad(fecha_nacimiento)) STORED
);


ALTER TABLE public.persona OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 16712)
-- Name: persona_revista; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persona_revista (
    dni character varying(10) NOT NULL,
    serial character varying(20) NOT NULL
);


ALTER TABLE public.persona_revista OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 16538)
-- Name: persona_telefono; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persona_telefono (
    dni character varying(10) NOT NULL,
    telefono character varying(15) NOT NULL
);


ALTER TABLE public.persona_telefono OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 16548)
-- Name: revista; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.revista (
    serial character varying(20) NOT NULL,
    nombre character varying(30) NOT NULL,
    frecuencia character varying(20) NOT NULL,
    nombre_tipo_revista character varying(20) NOT NULL
);


ALTER TABLE public.revista OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 16553)
-- Name: tipos_revista; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipos_revista (
    nombre character varying(20) NOT NULL,
    descripcion character varying(100) NOT NULL
);


ALTER TABLE public.tipos_revista OWNER TO postgres;

--
-- TOC entry 2851 (class 0 OID 16528)
-- Dependencies: 203
-- Data for Name: direccion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.direccion (dni, numero, calle) FROM stdin;
95555666	10	Avenida David Rojas
95666555	10	Avenida David Rojas
\.


--
-- TOC entry 2855 (class 0 OID 16701)
-- Dependencies: 207
-- Data for Name: persona; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.persona (dni, nombre, apellido, fecha_nacimiento) FROM stdin;
95555666	José	Martinez	1990-01-22
95666555	María	Rodriguez	1989-10-05
99555777	Pedro	Kinx	1999-06-07
95222333	Erika	Sanz	2005-10-05
\.


--
-- TOC entry 2856 (class 0 OID 16712)
-- Dependencies: 208
-- Data for Name: persona_revista; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.persona_revista (dni, serial) FROM stdin;
95555666	1312343
95666555	1312343
95555666	4234244
\.


--
-- TOC entry 2852 (class 0 OID 16538)
-- Dependencies: 204
-- Data for Name: persona_telefono; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.persona_telefono (dni, telefono) FROM stdin;
95555666	1128755555
95555666	11287555666
95666555	11225487587
99555777	11695874851
95222333	11562154822
95222333	11558466582
\.


--
-- TOC entry 2853 (class 0 OID 16548)
-- Dependencies: 205
-- Data for Name: revista; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.revista (serial, nombre, frecuencia, nombre_tipo_revista) FROM stdin;
3344661	Pasarelas	mensual	Moda
1312343	BiciTrek	semanal	Deportes
4234244	Músculos	semanal	Fitness
3575566	Vogue	mensual	Moda
\.


--
-- TOC entry 2854 (class 0 OID 16553)
-- Dependencies: 206
-- Data for Name: tipos_revista; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tipos_revista (nombre, descripcion) FROM stdin;
Deportes	Noticias y artículos deportivos
Moda	Artículos de moda
Fitness	Salud en general
\.


--
-- TOC entry 2717 (class 2606 OID 16706)
-- Name: persona persona_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (dni);


--
-- TOC entry 2719 (class 2606 OID 16716)
-- Name: persona_revista persona_revista_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona_revista
    ADD CONSTRAINT persona_revista_pkey PRIMARY KEY (dni, serial);


--
-- TOC entry 2709 (class 2606 OID 16532)
-- Name: direccion persona_telefono_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.direccion
    ADD CONSTRAINT persona_telefono_pkey PRIMARY KEY (dni, numero);


--
-- TOC entry 2711 (class 2606 OID 16542)
-- Name: persona_telefono persona_telefono_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona_telefono
    ADD CONSTRAINT persona_telefono_pkey1 PRIMARY KEY (dni, telefono);


--
-- TOC entry 2713 (class 2606 OID 16568)
-- Name: revista revista_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.revista
    ADD CONSTRAINT revista_pkey PRIMARY KEY (serial);


--
-- TOC entry 2715 (class 2606 OID 16557)
-- Name: tipos_revista tipos_revista_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipos_revista
    ADD CONSTRAINT tipos_revista_pkey PRIMARY KEY (nombre);


--
-- TOC entry 2720 (class 2606 OID 16707)
-- Name: direccion direccion_dni_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.direccion
    ADD CONSTRAINT direccion_dni_fkey FOREIGN KEY (dni) REFERENCES public.persona(dni) NOT VALID;


--
-- TOC entry 2723 (class 2606 OID 16717)
-- Name: persona_revista persona_revista_dni_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona_revista
    ADD CONSTRAINT persona_revista_dni_fkey FOREIGN KEY (dni) REFERENCES public.persona(dni);


--
-- TOC entry 2724 (class 2606 OID 16722)
-- Name: persona_revista persona_revista_serial_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona_revista
    ADD CONSTRAINT persona_revista_serial_fkey FOREIGN KEY (serial) REFERENCES public.revista(serial);


--
-- TOC entry 2721 (class 2606 OID 16727)
-- Name: persona_telefono persona_telefono_dni_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona_telefono
    ADD CONSTRAINT persona_telefono_dni_fkey FOREIGN KEY (dni) REFERENCES public.persona(dni) NOT VALID;


--
-- TOC entry 2722 (class 2606 OID 16569)
-- Name: revista revista_nombre_tipo_revista_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.revista
    ADD CONSTRAINT revista_nombre_tipo_revista_fkey FOREIGN KEY (nombre_tipo_revista) REFERENCES public.tipos_revista(nombre) NOT VALID;


-- Completed on 2020-07-16 17:05:03

--
-- PostgreSQL database dump complete
--

