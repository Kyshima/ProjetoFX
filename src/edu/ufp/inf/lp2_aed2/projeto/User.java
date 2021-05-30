package edu.ufp.inf.lp2_aed2.projeto;

import edu.princeton.cs.algs4.*;

import java.lang.String;
import java.util.Arrays;

public class User {

    public String nome;
    public String tipo;
    public int travelbug;

    public User(String nome, String tipo, int travelbug) {
        this.nome = nome;
        this.tipo = tipo;
        this.travelbug = travelbug;
    }

    public User(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public User() {
        this.nome = "";
        this.tipo = "";
        this.travelbug = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTravelbug() {
        return travelbug;
    }

    public void setTravelbug(int travelbug) {
        this.travelbug = travelbug;
    }

    /**
     * Cria um User e adiciona-o na sua ST
     *
     * @param id      - id do user a adicionar
     * @param nome    - nome do user a adicionar
     * @param tipo    - tipo do user a adicionar (basic, premium, admin)
     * @param sizes   - array com o numero total das variaveis (sendo a posicao 0 o numero de users
     * @param user_st - ST dos users
     */
    public void addUser(int id, String nome, String tipo, int[] sizes, SequentialSearchST<Integer, User> user_st) {
        if (user_st.contains(id)) {
            System.out.println("Erro na Inserção do USER:\tO ID " + id + " já existe!");
        } else {
            User new_u = new User(nome, tipo);
            user_st.put(id, new_u);
            System.out.println("User " + id + " adicionado com sucesso! -> " + new_u);
            sizes[0]++;
        }
    }

    /**
     * Remove o User, usando o id, da ST
     *
     * @param id      - id do user a remover
     * @param sizes   - array com o numero total das variaveis (sendo a posicao 0 o numero de users)
     * @param user_st - ST dos users
     */
    public void removeUser(int id, int[] sizes, SequentialSearchST<Integer, User> user_st) {
        if (user_st.contains(id)) {
            user_st.delete(id);
            sizes[0]--;
            System.out.println("User " + id + " removido com sucesso!");
        } else {
            System.out.println("Erro na Remoção do USER:\tO ID " + id + " não existe!");
        }
    }

    /**
     * Edita um parametro do user, recebendo o parametro a alterar, baseado nas variaveis da classe
     *
     * @param tipo     - parametro que se vai alterar
     * @param conteudo - conteudo com o qual se vai alterar
     * @param id       - id do user
     * @param user_st  - ST dos Users
     */
    public void editUser(String tipo, String conteudo, int id, SequentialSearchST<Integer, User> user_st) {
        if (user_st.get(id) != null && id <= user_st.size()) {
            switch (tipo) {
                case "nome": {
                    user_st.get(id).nome = conteudo;
                    System.out.println("User " + id + " editado com sucesso! -> " + user_st.get(id));
                }
                case "tipo": {
                    user_st.get(id).tipo = conteudo;
                    System.out.println("User " + id + " editado com sucesso! -> " + user_st.get(id));
                }
                case "travelbug": {
                    user_st.get(id).travelbug = Integer.parseInt(conteudo);
                    System.out.println("User " + id + " editado com sucesso! -> " + user_st.get(id));
                }
                default: System.out.println("Tipo introduzido invalido na ediçao do User");
            }
        } else {
            System.out.println("Erro na Edição do USER:\tO ID " + id + " não existe!");
        }
    }

    /**
     * Lista todas as caches que um user visitou
     *
     * @param nome   - nome do user a procurar
     * @param sizes  - array com o numero total de user
     * @param geo_st - ST das Geocaches
     * @param reg_st - ST da Regiao
     * @param log    - ST do Historico dos Users
     */
    public void searchVisitedCacheGlobal(String nome, int[] sizes, SequentialSearchST<Integer, Geocache> geo_st, SequentialSearchST<Integer, Regiao> reg_st, RedBlackBST<Integer, HistoricoVisited> log) {
        for (int i = 0; i <= sizes[6]; i++) {
            if (log.get(i) != null && log.get(i).user.equals(nome)) {
                System.out.println("Caches visitadas por " + nome + " :");
                for (int j = 0; j < log.get(i).n_visited; j++) {
                    int z = log.get(i).visited[j];
                    if (geo_st.get(z) != null)
                        System.out.println("\t\t" + log.get(i).date[j] + "\t--> Geocache " + z + "\tpertencente a Regiao " + capitalize(reg_st.get(geo_st.get(z).id_reg).nome));
                }
                System.out.println();
                return;
            }
        }
        System.out.println("User nao encontrado/existe!");
    }

    /**
     * Lista as caches que um user visitou numa certa região
     *
     * @param nome   - nome do user a procurar
     * @param id_reg - id da regiao para delimitar a pesquisa
     * @param sizes  - array com o numero total de user
     * @param geo_st - ST das Geocaches
     * @param reg_st - ST da Regiao
     * @param log    - ST do Historico dos Users
     */
    public void searchVisitedCacheRegiao(String nome, int id_reg, int[] sizes, SequentialSearchST<Integer, Geocache> geo_st, SequentialSearchST<Integer, Regiao> reg_st, RedBlackBST<Integer, HistoricoVisited> log) {
        for (int i = 0; i <= sizes[6]; i++) {
            if (log.get(i) != null && log.get(i).user.equals(nome)) {
                System.out.println("Caches visitadas por " + nome + " na Regiao " + capitalize(reg_st.get(id_reg).nome));
                for (int j = 0; j < log.get(i).n_visited; j++) {
                    if (geo_st.get(log.get(i).visited[j]) != null && geo_st.get(log.get(i).visited[j]).id_reg == id_reg) {
                        int z = log.get(i).visited[j];
                        System.out.println("\t\t" + log.get(i).date[j] + "\t--> Geocache " + z);
                    }
                }
                System.out.println();
                return;
            }
        }
        System.out.println("User nao encontrado/existe!");
    }

    /**
     * Lista todas as geocaches nao visitadas pelo user
     *
     * @param nome   - nome do user a procurar
     * @param sizes  - array com o numero total de user
     * @param geo_st - ST das Geocaches
     * @param reg_st - ST da Regiao
     * @param log    - ST do Historico dos Users
     */
    public void searchMissingCacheGlobal(String nome, int[] sizes, SequentialSearchST<Integer, Geocache> geo_st, SequentialSearchST<Integer, Regiao> reg_st, RedBlackBST<Integer, HistoricoVisited> log) {
        for (int i = 1; i <= sizes[6]; i++) {                                                                                 // Percorre os Users

            if (log.get(i) != null && log.get(i).user.equals(nome)) {                                                           // Se size = 10, com user a visitar 3
                int[] aux = new int[log.get(i).n_visited];                                                                      // aux = 3 pos
                int[] inverso = new int[sizes[2] - log.get(i).n_visited];                                                       // inverso = 7 pos

                System.out.println("Caches que faltam visitar por " + nome + " :");
                for (int j = 0; j < log.get(i).n_visited; j++) {                                                                  // J percorre 3x
                    aux[j] = log.get(i).visited[j];                                                                               // Escreve na aux as geos visitadas
                }

                int current = 0, existe = 0, tamanho = sizes[2];
                for (int j = 1; j <= tamanho; j++) {                                                                              // Percorre as geos todas
                    if (geo_st.get(j) != null) {
                        for (int z = 0; z < log.get(i).n_visited; z++) {                                                            // Percorre o nr de geos que o user visitou
                            if (aux[z] == j) {
                                existe = 1;
                                break;
                            }
                        }
                        if (existe != 1) {
                            inverso[current] = j;
                            current++;
                        }
                        existe = 0;
                    } else tamanho++;
                }
                for (int b : inverso) {
                    System.out.println("\t\tGeocache " + b + "\tpertencente a Regiao " + capitalize(reg_st.get(geo_st.get(b).id_reg).nome));
                }
                System.out.println();
                return;
            }
        }
        System.out.println("User nao encontrado/existe!");
    }

    /**
     * Lista as caches que um user ainda nao visitou numa certa região
     *
     * @param nome   - nome do user a procurar
     * @param id_reg - id da regiao para delimitar a pesquisa
     * @param sizes  - array com o numero total de user
     * @param geo_st - ST das Geocaches
     * @param reg_st - ST da Regiao
     * @param log    - ST do Historico dos Users
     */
    public void searchMissingCacheRegiao(String nome, int id_reg, int[] sizes, SequentialSearchST<Integer, Geocache> geo_st, SequentialSearchST<Integer, Regiao> reg_st, RedBlackBST<Integer, HistoricoVisited> log) {
        for (int i = 1; i <= sizes[6]; i++) {

            if (log.get(i) != null && log.get(i).user.equals(nome)) {
                int[] aux = new int[log.get(i).n_visited];
                int[] inverso = new int[sizes[2] - log.get(i).n_visited];

                System.out.println("Caches que faltam visitar por " + nome + " :");
                for (int j = 0; j < log.get(i).n_visited; j++) {
                    aux[j] = log.get(i).visited[j];
                }

                int current = 0, existe = 0, tamanho = sizes[2];
                for (int j = 1; j <= tamanho; j++) {
                    if (geo_st.get(j) != null) {
                        for (int z = 0; z < log.get(i).n_visited; z++) {

                            if (aux[z] == j) {
                                existe = 1;
                                break;
                            } else tamanho++;
                        }
                        if (existe != 1) {
                            inverso[current] = j;
                            current++;
                        }
                        existe = 0;
                    }
                }
                for (int b : inverso) {
                    if (geo_st.get(b).id_reg == id_reg)
                        System.out.println("\t\tGeocache " + b + "\tpertencente a Regiao " + capitalize(reg_st.get(geo_st.get(b).id_reg).nome));
                }
                System.out.println();
                return;
            }
        }
        System.out.println("User nao encontrado/existe!");
    }

    @Override
    public String toString() {
        return "User {" +
                "nome = '" + nome + '\'' +
                ", tipo = '" + tipo + '\'' +
                //", travelbug=" + travelbug +
                "}";
    }

    /**
     * Coloca uma String com a primeira letra em maiuscula
     *
     * @param str - String para por com maiuscula
     * @return - Da return a string com a primeira letra maiuscula
     */
    public static String capitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuffer(strLen)
                .append(Character.toTitleCase(str.charAt(0)))
                .append(str.substring(1))
                .toString();
    }
}