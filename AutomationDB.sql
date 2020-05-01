--
-- PostgreSQL database dump
--

-- Dumped from database version 11.7 (Ubuntu 11.7-2.pgdg16.04+1)
-- Dumped by pg_dump version 12.1

-- Started on 2020-05-01 11:26:47

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

--
-- TOC entry 207 (class 1259 OID 5911581)
-- Name: tblanswer; Type: TABLE; Schema: public; Owner: utufnbbozfaphi
--

CREATE TABLE public.tblanswer (
    answerid integer NOT NULL,
    takensurveyid integer NOT NULL,
    questionid integer NOT NULL,
    answer integer NOT NULL,
    active boolean DEFAULT true NOT NULL
);


ALTER TABLE public.tblanswer OWNER TO utufnbbozfaphi;

--
-- TOC entry 206 (class 1259 OID 5911579)
-- Name: tblanswers_answersid_seq; Type: SEQUENCE; Schema: public; Owner: utufnbbozfaphi
--

CREATE SEQUENCE public.tblanswers_answersid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblanswers_answersid_seq OWNER TO utufnbbozfaphi;

--
-- TOC entry 3923 (class 0 OID 0)
-- Dependencies: 206
-- Name: tblanswers_answersid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utufnbbozfaphi
--

ALTER SEQUENCE public.tblanswers_answersid_seq OWNED BY public.tblanswer.answerid;


--
-- TOC entry 209 (class 1259 OID 5911595)
-- Name: tblquestion; Type: TABLE; Schema: public; Owner: utufnbbozfaphi
--

CREATE TABLE public.tblquestion (
    questionid integer NOT NULL,
    questiontypeid integer NOT NULL,
    question character varying(1000) NOT NULL,
    active boolean DEFAULT true NOT NULL
);


ALTER TABLE public.tblquestion OWNER TO utufnbbozfaphi;

--
-- TOC entry 208 (class 1259 OID 5911593)
-- Name: tblquestion_questionid_seq; Type: SEQUENCE; Schema: public; Owner: utufnbbozfaphi
--

CREATE SEQUENCE public.tblquestion_questionid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblquestion_questionid_seq OWNER TO utufnbbozfaphi;

--
-- TOC entry 3924 (class 0 OID 0)
-- Dependencies: 208
-- Name: tblquestion_questionid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utufnbbozfaphi
--

ALTER SEQUENCE public.tblquestion_questionid_seq OWNED BY public.tblquestion.questionid;


--
-- TOC entry 205 (class 1259 OID 5911573)
-- Name: tblquestioninsurvey; Type: TABLE; Schema: public; Owner: utufnbbozfaphi
--

CREATE TABLE public.tblquestioninsurvey (
    questioninsurvey integer NOT NULL,
    surveytypeid integer NOT NULL,
    questionid integer NOT NULL
);


ALTER TABLE public.tblquestioninsurvey OWNER TO utufnbbozfaphi;

--
-- TOC entry 204 (class 1259 OID 5911571)
-- Name: tblquestioninsurvey_questioninsurvey_seq; Type: SEQUENCE; Schema: public; Owner: utufnbbozfaphi
--

CREATE SEQUENCE public.tblquestioninsurvey_questioninsurvey_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblquestioninsurvey_questioninsurvey_seq OWNER TO utufnbbozfaphi;

--
-- TOC entry 3925 (class 0 OID 0)
-- Dependencies: 204
-- Name: tblquestioninsurvey_questioninsurvey_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utufnbbozfaphi
--

ALTER SEQUENCE public.tblquestioninsurvey_questioninsurvey_seq OWNED BY public.tblquestioninsurvey.questioninsurvey;


--
-- TOC entry 211 (class 1259 OID 5911607)
-- Name: tblquestiontype; Type: TABLE; Schema: public; Owner: utufnbbozfaphi
--

CREATE TABLE public.tblquestiontype (
    questiontypeid integer NOT NULL,
    type character varying(50) NOT NULL,
    description character varying(100) NOT NULL,
    numberofoptions integer NOT NULL,
    active boolean DEFAULT true NOT NULL
);


ALTER TABLE public.tblquestiontype OWNER TO utufnbbozfaphi;

--
-- TOC entry 210 (class 1259 OID 5911605)
-- Name: tblquestiontype_questiontypeid_seq; Type: SEQUENCE; Schema: public; Owner: utufnbbozfaphi
--

CREATE SEQUENCE public.tblquestiontype_questiontypeid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblquestiontype_questiontypeid_seq OWNER TO utufnbbozfaphi;

--
-- TOC entry 3926 (class 0 OID 0)
-- Dependencies: 210
-- Name: tblquestiontype_questiontypeid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utufnbbozfaphi
--

ALTER SEQUENCE public.tblquestiontype_questiontypeid_seq OWNED BY public.tblquestiontype.questiontypeid;


--
-- TOC entry 201 (class 1259 OID 5911557)
-- Name: tblsurvey; Type: TABLE; Schema: public; Owner: utufnbbozfaphi
--

CREATE TABLE public.tblsurvey (
    surveyid integer NOT NULL,
    surveytypeid integer NOT NULL,
    name character varying(25) NOT NULL,
    created timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    active boolean DEFAULT true NOT NULL
);


ALTER TABLE public.tblsurvey OWNER TO utufnbbozfaphi;

--
-- TOC entry 200 (class 1259 OID 5911555)
-- Name: tblsurvey_surveyid_seq; Type: SEQUENCE; Schema: public; Owner: utufnbbozfaphi
--

CREATE SEQUENCE public.tblsurvey_surveyid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblsurvey_surveyid_seq OWNER TO utufnbbozfaphi;

--
-- TOC entry 3927 (class 0 OID 0)
-- Dependencies: 200
-- Name: tblsurvey_surveyid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utufnbbozfaphi
--

ALTER SEQUENCE public.tblsurvey_surveyid_seq OWNED BY public.tblsurvey.surveyid;


--
-- TOC entry 203 (class 1259 OID 5911565)
-- Name: tblsurveytype; Type: TABLE; Schema: public; Owner: utufnbbozfaphi
--

CREATE TABLE public.tblsurveytype (
    surveytypeid integer NOT NULL,
    type character varying(50) NOT NULL,
    description character varying(100) NOT NULL,
    active boolean DEFAULT true NOT NULL
);


ALTER TABLE public.tblsurveytype OWNER TO utufnbbozfaphi;

--
-- TOC entry 202 (class 1259 OID 5911563)
-- Name: tblsurveytype_surveytypeid_seq; Type: SEQUENCE; Schema: public; Owner: utufnbbozfaphi
--

CREATE SEQUENCE public.tblsurveytype_surveytypeid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblsurveytype_surveytypeid_seq OWNER TO utufnbbozfaphi;

--
-- TOC entry 3928 (class 0 OID 0)
-- Dependencies: 202
-- Name: tblsurveytype_surveytypeid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utufnbbozfaphi
--

ALTER SEQUENCE public.tblsurveytype_surveytypeid_seq OWNED BY public.tblsurveytype.surveytypeid;


--
-- TOC entry 199 (class 1259 OID 5911549)
-- Name: tbltakensurvey; Type: TABLE; Schema: public; Owner: utufnbbozfaphi
--

CREATE TABLE public.tbltakensurvey (
    takensurveyid integer NOT NULL,
    surveyid integer NOT NULL,
    teamsid integer NOT NULL,
    score double precision NOT NULL,
    "timestamp" timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.tbltakensurvey OWNER TO utufnbbozfaphi;

--
-- TOC entry 198 (class 1259 OID 5911547)
-- Name: tbltakensurvey_takensurveyid_seq; Type: SEQUENCE; Schema: public; Owner: utufnbbozfaphi
--

CREATE SEQUENCE public.tbltakensurvey_takensurveyid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tbltakensurvey_takensurveyid_seq OWNER TO utufnbbozfaphi;

--
-- TOC entry 3929 (class 0 OID 0)
-- Dependencies: 198
-- Name: tbltakensurvey_takensurveyid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utufnbbozfaphi
--

ALTER SEQUENCE public.tbltakensurvey_takensurveyid_seq OWNED BY public.tbltakensurvey.takensurveyid;


--
-- TOC entry 197 (class 1259 OID 5911539)
-- Name: tblteams; Type: TABLE; Schema: public; Owner: utufnbbozfaphi
--

CREATE TABLE public.tblteams (
    teamid integer NOT NULL,
    teamname character varying(25) NOT NULL,
    active boolean DEFAULT true NOT NULL
);


ALTER TABLE public.tblteams OWNER TO utufnbbozfaphi;

--
-- TOC entry 196 (class 1259 OID 5911537)
-- Name: tblteams_teamsid_seq; Type: SEQUENCE; Schema: public; Owner: utufnbbozfaphi
--

CREATE SEQUENCE public.tblteams_teamsid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblteams_teamsid_seq OWNER TO utufnbbozfaphi;

--
-- TOC entry 3930 (class 0 OID 0)
-- Dependencies: 196
-- Name: tblteams_teamsid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utufnbbozfaphi
--

ALTER SEQUENCE public.tblteams_teamsid_seq OWNED BY public.tblteams.teamid;


--
-- TOC entry 213 (class 1259 OID 5911615)
-- Name: tblusers; Type: TABLE; Schema: public; Owner: utufnbbozfaphi
--

CREATE TABLE public.tblusers (
    userid integer NOT NULL,
    username character varying(20) NOT NULL,
    password character varying(20) NOT NULL
);


ALTER TABLE public.tblusers OWNER TO utufnbbozfaphi;

--
-- TOC entry 212 (class 1259 OID 5911613)
-- Name: tblusers_userid_seq; Type: SEQUENCE; Schema: public; Owner: utufnbbozfaphi
--

CREATE SEQUENCE public.tblusers_userid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tblusers_userid_seq OWNER TO utufnbbozfaphi;

--
-- TOC entry 3931 (class 0 OID 0)
-- Dependencies: 212
-- Name: tblusers_userid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utufnbbozfaphi
--

ALTER SEQUENCE public.tblusers_userid_seq OWNED BY public.tblusers.userid;


--
-- TOC entry 3760 (class 2604 OID 5911584)
-- Name: tblanswer answerid; Type: DEFAULT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblanswer ALTER COLUMN answerid SET DEFAULT nextval('public.tblanswers_answersid_seq'::regclass);


--
-- TOC entry 3762 (class 2604 OID 5911598)
-- Name: tblquestion questionid; Type: DEFAULT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblquestion ALTER COLUMN questionid SET DEFAULT nextval('public.tblquestion_questionid_seq'::regclass);


--
-- TOC entry 3759 (class 2604 OID 5911576)
-- Name: tblquestioninsurvey questioninsurvey; Type: DEFAULT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblquestioninsurvey ALTER COLUMN questioninsurvey SET DEFAULT nextval('public.tblquestioninsurvey_questioninsurvey_seq'::regclass);


--
-- TOC entry 3764 (class 2604 OID 5911610)
-- Name: tblquestiontype questiontypeid; Type: DEFAULT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblquestiontype ALTER COLUMN questiontypeid SET DEFAULT nextval('public.tblquestiontype_questiontypeid_seq'::regclass);


--
-- TOC entry 3754 (class 2604 OID 5911560)
-- Name: tblsurvey surveyid; Type: DEFAULT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblsurvey ALTER COLUMN surveyid SET DEFAULT nextval('public.tblsurvey_surveyid_seq'::regclass);


--
-- TOC entry 3757 (class 2604 OID 5911568)
-- Name: tblsurveytype surveytypeid; Type: DEFAULT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblsurveytype ALTER COLUMN surveytypeid SET DEFAULT nextval('public.tblsurveytype_surveytypeid_seq'::regclass);


--
-- TOC entry 3752 (class 2604 OID 5911552)
-- Name: tbltakensurvey takensurveyid; Type: DEFAULT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tbltakensurvey ALTER COLUMN takensurveyid SET DEFAULT nextval('public.tbltakensurvey_takensurveyid_seq'::regclass);


--
-- TOC entry 3750 (class 2604 OID 5911542)
-- Name: tblteams teamid; Type: DEFAULT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblteams ALTER COLUMN teamid SET DEFAULT nextval('public.tblteams_teamsid_seq'::regclass);


--
-- TOC entry 3766 (class 2604 OID 5911618)
-- Name: tblusers userid; Type: DEFAULT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblusers ALTER COLUMN userid SET DEFAULT nextval('public.tblusers_userid_seq'::regclass);


--
-- TOC entry 3780 (class 2606 OID 5911586)
-- Name: tblanswer tblanswers_pkey; Type: CONSTRAINT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblanswer
    ADD CONSTRAINT tblanswers_pkey PRIMARY KEY (answerid);


--
-- TOC entry 3782 (class 2606 OID 5911603)
-- Name: tblquestion tblquestion_pkey; Type: CONSTRAINT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblquestion
    ADD CONSTRAINT tblquestion_pkey PRIMARY KEY (questionid);


--
-- TOC entry 3778 (class 2606 OID 5911578)
-- Name: tblquestioninsurvey tblquestioninsurvey_pkey; Type: CONSTRAINT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblquestioninsurvey
    ADD CONSTRAINT tblquestioninsurvey_pkey PRIMARY KEY (questioninsurvey);


--
-- TOC entry 3784 (class 2606 OID 5911612)
-- Name: tblquestiontype tblquestiontype_pkey; Type: CONSTRAINT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblquestiontype
    ADD CONSTRAINT tblquestiontype_pkey PRIMARY KEY (questiontypeid);


--
-- TOC entry 3774 (class 2606 OID 5911562)
-- Name: tblsurvey tblsurvey_pkey; Type: CONSTRAINT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblsurvey
    ADD CONSTRAINT tblsurvey_pkey PRIMARY KEY (surveyid);


--
-- TOC entry 3776 (class 2606 OID 5911570)
-- Name: tblsurveytype tblsurveytype_pkey; Type: CONSTRAINT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblsurveytype
    ADD CONSTRAINT tblsurveytype_pkey PRIMARY KEY (surveytypeid);


--
-- TOC entry 3772 (class 2606 OID 5911554)
-- Name: tbltakensurvey tbltakensurvey_pkey; Type: CONSTRAINT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tbltakensurvey
    ADD CONSTRAINT tbltakensurvey_pkey PRIMARY KEY (takensurveyid);


--
-- TOC entry 3768 (class 2606 OID 5911544)
-- Name: tblteams tblteams_pkey; Type: CONSTRAINT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblteams
    ADD CONSTRAINT tblteams_pkey PRIMARY KEY (teamid);


--
-- TOC entry 3770 (class 2606 OID 5938476)
-- Name: tblteams tblteams_team_key; Type: CONSTRAINT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblteams
    ADD CONSTRAINT tblteams_team_key UNIQUE (teamname);


--
-- TOC entry 3786 (class 2606 OID 5911621)
-- Name: tblusers tblusers_pkey; Type: CONSTRAINT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblusers
    ADD CONSTRAINT tblusers_pkey PRIMARY KEY (userid);


--
-- TOC entry 3793 (class 2606 OID 5911658)
-- Name: tblanswer tblanswers_questionid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblanswer
    ADD CONSTRAINT tblanswers_questionid_fkey FOREIGN KEY (questionid) REFERENCES public.tblquestion(questionid);


--
-- TOC entry 3792 (class 2606 OID 5911653)
-- Name: tblanswer tblanswers_takensurveyid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblanswer
    ADD CONSTRAINT tblanswers_takensurveyid_fkey FOREIGN KEY (takensurveyid) REFERENCES public.tbltakensurvey(takensurveyid);


--
-- TOC entry 3794 (class 2606 OID 5911663)
-- Name: tblquestion tblquestion_questiontypeid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblquestion
    ADD CONSTRAINT tblquestion_questiontypeid_fkey FOREIGN KEY (questiontypeid) REFERENCES public.tblquestiontype(questiontypeid);


--
-- TOC entry 3790 (class 2606 OID 5911648)
-- Name: tblquestioninsurvey tblquestioninsurvey_questionid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblquestioninsurvey
    ADD CONSTRAINT tblquestioninsurvey_questionid_fkey FOREIGN KEY (questionid) REFERENCES public.tblquestion(questionid);


--
-- TOC entry 3791 (class 2606 OID 5989898)
-- Name: tblquestioninsurvey tblquestioninsurvey_surveytypeid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblquestioninsurvey
    ADD CONSTRAINT tblquestioninsurvey_surveytypeid_fkey FOREIGN KEY (surveytypeid) REFERENCES public.tblsurveytype(surveytypeid) NOT VALID;


--
-- TOC entry 3789 (class 2606 OID 5911632)
-- Name: tblsurvey tblsurvey_surveytypeid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tblsurvey
    ADD CONSTRAINT tblsurvey_surveytypeid_fkey FOREIGN KEY (surveytypeid) REFERENCES public.tblsurveytype(surveytypeid);


--
-- TOC entry 3787 (class 2606 OID 5911622)
-- Name: tbltakensurvey tbltakensurvey_surveyid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tbltakensurvey
    ADD CONSTRAINT tbltakensurvey_surveyid_fkey FOREIGN KEY (surveyid) REFERENCES public.tblsurvey(surveyid);


--
-- TOC entry 3788 (class 2606 OID 5911627)
-- Name: tbltakensurvey tbltakensurvey_teamsid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: utufnbbozfaphi
--

ALTER TABLE ONLY public.tbltakensurvey
    ADD CONSTRAINT tbltakensurvey_teamsid_fkey FOREIGN KEY (teamsid) REFERENCES public.tblteams(teamid);


--
-- TOC entry 3921 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: utufnbbozfaphi
--

REVOKE ALL ON SCHEMA public FROM postgres;
REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO utufnbbozfaphi;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- TOC entry 3922 (class 0 OID 0)
-- Dependencies: 643
-- Name: LANGUAGE plpgsql; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON LANGUAGE plpgsql TO utufnbbozfaphi;


-- Completed on 2020-05-01 11:26:58

--
-- PostgreSQL database dump complete
--

