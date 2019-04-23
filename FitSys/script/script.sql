
CREATE TABLE Parametrizao (
				logo BYTEA NOT NULL, razao_social VARCHAR(50) NOT NULL,
                backgound BYTEA,
                cnpj VARCHAR(14) NOT NULL,
                cor_primaria VARCHAR(10) NOT NULL,
                cor_secundaria VARCHAR(10) NOT NULL,
                endereco VARCHAR(50) NOT NULL,
                cidade VARCHAR(50) NOT NULL,
                cep VARCHAR(11) NOT NULL
);


CREATE TABLE Modalidade (
                mod_Cod SERIAL NOT NULL,
                mod_Nome VARCHAR(50) NOT NULL,
                mod_Preco NUMERIC(6,2) NOT NULL,
                CONSTRAINT mod_cod PRIMARY KEY (mod_Cod)
);


CREATE TABLE Pacote (
                pct_Cod SERIAL NOT NULL,
                pct_Desc VARCHAR(50) NOT NULL,
                pct_PorcDesconto NUMERIC(6) NOT NULL,
                pct_Total NUMERIC(6,2) NOT NULL,
                CONSTRAINT pct_cod PRIMARY KEY (pct_Cod)
);


CREATE TABLE Pacote_modalidade (
                pctmod_Cod SERIAL NOT NULL,
                pct_Cod NUMERIC(6) NOT NULL,
                mod_Cod NUMERIC(6) NOT NULL,
                CONSTRAINT pctmod_cod PRIMARY KEY (pctmod_Cod, pct_Cod, mod_Cod)
);


CREATE TABLE TipoDespesa (
                tpd_Cod SERIAL NOT NULL,
                tpd_Desc VARCHAR(50) NOT NULL,
                CONSTRAINT tpd_cod PRIMARY KEY (tpd_Cod)
);


CREATE TABLE Despesa (
                desp_Cod SERIAL NOT NULL,
                desp_Valor NUMERIC(6,2) NOT NULL,
                desp_DTVencimento DATE NOT NULL,
                tpd_Cod NUMERIC(6) NOT NULL,
                desp_DTPagamento DATE,
                CONSTRAINT desp_cod PRIMARY KEY (desp_Cod)
);


CREATE TABLE Funcionario (
                fun_CPF VARCHAR(11) NOT NULL,
                fun_Nome VARCHAR(50) NOT NULL,
                fun_Tel VARCHAR(12) NOT NULL,
                fun_Rua VARCHAR(50) NOT NULL,
                fun_dtNasc DATE NOT NULL,
                fun_Cidade VARCHAR(50) NOT NULL,
                fun_Cep VARCHAR(11) NOT NULL,
                fun_Email VARCHAR(50) NOT NULL,
                fun_Cargo VARCHAR(50) NOT NULL,
                fun_Senha VARCHAR(50) NOT NULL,
                fun_nivel CHAR NOT NULL,
                CONSTRAINT fun_cpf PRIMARY KEY (fun_CPF)
);


CREATE TABLE Avaliacao (
                av_Cod SERIAL NOT NULL,
                av_DTAgendamento DATE NOT NULL,
                av_DTAvaliacao DATE,
                av_Peso NUMERIC(6,2),
                av_Torax NUMERIC(6,2),
                av_Altura NUMERIC(6,2),
                av_Abdomen NUMERIC(6,2),
                av_BracoDireito NUMERIC(6,2),
                av_BracoEsquerdo NUMERIC(6,2),
                av_CoxaDireita NUMERIC(6,2),
                av_CoxaEsquerda NUMERIC(6,2),
                av_Cintura NUMERIC(6,2),
                av_Quadril NUMERIC(6,2),
                av_PanturrilhaDireita NUMERIC(6,2),
                av_PanturrilhaEsquerda NUMERIC(6,2),
                fun_CPF VARCHAR(11) NOT NULL,
                CONSTRAINT av_cod PRIMARY KEY (av_Cod)
);


CREATE TABLE Aluno (
                alu_Cpf VARCHAR NOT NULL,
                alu_Nome VARCHAR(50) NOT NULL,
                alu_Tel VARCHAR(12) NOT NULL,
                alu_Rua VARCHAR(50) NOT NULL,
                alu_Cep VARCHAR(11) NOT NULL,
                alu_Cidade VARCHAR(50) NOT NULL,
                alu_dtNasc DATE NOT NULL,
                alu_Email VARCHAR(50) NOT NULL,
                alu_Sexo CHAR NOT NULL,
                CONSTRAINT alu_cod PRIMARY KEY (alu_Cpf)
);


CREATE TABLE Matricula (
                mat_Cod SERIAL NOT NULL,
                mat_DTMat DATE NOT NULL,
                alu_Cpf VARCHAR NOT NULL,
                pct_Cod NUMERIC(6) NOT NULL,
                CONSTRAINT mat_cod PRIMARY KEY (mat_Cod)
);


CREATE TABLE Presenca (
                pre_Cod SERIAL NOT NULL,
                pre_DataHora TIMESTAMP NOT NULL,
                mat_Cod NUMERIC(6) NOT NULL,
                CONSTRAINT pre_cod PRIMARY KEY (pre_Cod)
);


CREATE TABLE Mensalidade (
                men_Cod SERIAL NOT NULL,
                men_Valor NUMERIC(6,2) NOT NULL,
                men_DTVenc DATE NOT NULL,
                mat_Cod NUMERIC(6) NOT NULL,
                CONSTRAINT men_cod PRIMARY KEY (men_Cod)
);


CREATE TABLE Treino (
                treino_Cod SERIAL NOT NULL,
                treino_Data DATE NOT NULL,
                mat_Codigo INTEGER NOT NULL,
                fun_CPF VARCHAR(11) NOT NULL,
                mat_Cod NUMERIC(6) NOT NULL,
                CONSTRAINT treino_cod PRIMARY KEY (treino_Cod)
);


CREATE TABLE Grupo_Muscular (
                gru_Cod SERIAL NOT NULL,
                gru_Descricao VARCHAR(50) NOT NULL,
                CONSTRAINT gru_cod PRIMARY KEY (gru_Cod)
);


CREATE TABLE Exercicios (
                exe_Cod SERIAL NOT NULL,
                exe_Desc VARCHAR(50) NOT NULL,
                gru_Cod NUMERIC(6) NOT NULL,
                CONSTRAINT exc_cod PRIMARY KEY (exe_Cod)
);


CREATE TABLE Exercicio_Treino (
                exe_Cod SERIAL NOT NULL,
                treino_Cod NUMERIC(6) NOT NULL,
                ext_Tipo CHAR NOT NULL,
                ext_Repeticao INTEGER NOT NULL,
                ext_Serie INTEGER NOT NULL,
                ext_Carga INTEGER NOT NULL,
                ext_Dia VARCHAR(10) NOT NULL,
                CONSTRAINT ext_cod PRIMARY KEY (exe_Cod, treino_Cod)
);


ALTER TABLE Pacote_Modalidade ADD CONSTRAINT modalidade_pacote_modalidade_fk
FOREIGN KEY (mod_Cod)
REFERENCES Modalidade (mod_Cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE Pacote_Modalidade ADD CONSTRAINT pacote_pacote_modalidade_fk
FOREIGN KEY (pct_Cod)
REFERENCES Pacote (pct_Cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE Matricula ADD CONSTRAINT pacote_matricula_fk
FOREIGN KEY (pct_Cod)
REFERENCES Pacote (pct_Cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE Despesa ADD CONSTRAINT tipodespesa_despesa_fk
FOREIGN KEY (tpd_Cod)
REFERENCES TipoDespesa (tpd_Cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE Avaliacao ADD CONSTRAINT funcionario_avaliacao_fk
FOREIGN KEY (fun_CPF)
REFERENCES Funcionario (fun_CPF)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE Treino ADD CONSTRAINT funcionario_treino_fk
FOREIGN KEY (fun_CPF)
REFERENCES Funcionario (fun_CPF)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE Matricula ADD CONSTRAINT aluno_matricula_fk
FOREIGN KEY (alu_Cpf)
REFERENCES Aluno (alu_Cpf)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE Mensalidade ADD CONSTRAINT matricula_mensalidade_fk
FOREIGN KEY (mat_Cod)
REFERENCES Matricula (mat_Cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE Presenca ADD CONSTRAINT matricula_presenca_fk
FOREIGN KEY (mat_Cod)
REFERENCES Matricula (mat_Cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE Treino ADD CONSTRAINT matricula_treino_fk
FOREIGN KEY (mat_Cod)
REFERENCES Matricula (mat_Cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE Exercicio_Treino ADD CONSTRAINT treino_exercicio_treino_fk
FOREIGN KEY (treino_Cod)
REFERENCES Treino (treino_Cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE Exercicios ADD CONSTRAINT grupo_muscular_exercicios_fk
FOREIGN KEY (gru_Cod)
REFERENCES Grupo_Muscular (gru_Cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE Exercicio_Treino ADD CONSTRAINT exercicios_exercicio_treino_fk
FOREIGN KEY (exe_Cod)
REFERENCES Exercicios (exe_Cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
