package edu.ufp.inf.lp2_aed2.projeto;

import edu.princeton.cs.algs4.*;

import java.io.*;
import java.util.Scanner;

public class Comeco {
    public static void comeco_main(String[] args) {
        // Start
        // sizes = n_user, n_reg, n_geo,n_itens, n_tv, n_lig, n_visitado, n_histTB;
        int[] sizes = new int[8];
        for (int i = 0; i < 8; i++) sizes[i] = 0;

        SequentialSearchST<Integer, User> user_st = new SequentialSearchST<>();
        SequentialSearchST<Integer, Geocache> geo_st = new SequentialSearchST<>();
        SequentialSearchST<Integer, Item> item_st = new SequentialSearchST<>();
        SequentialSearchST<Integer, Ligacoes> lig_st = new SequentialSearchST<>();
        SequentialSearchST<Integer, Regiao> reg_st = new SequentialSearchST<>();
        SequentialSearchST<Integer, Travelbug> tvb_st = new SequentialSearchST<>();

        RedBlackBST<Integer, HistoricoVisited> hisV_st = new RedBlackBST<>();
        SequentialSearchST<Integer, HistoricoTB> hisTB_st = new SequentialSearchST<>();

        // Leitura do ficheiro input.txt
        try {
            Scanner scan = new Scanner(new BufferedReader(new FileReader("data/input.txt")));
            sizes[0] = scan.nextInt();
            scan.nextLine();
            // Leitura do user
            for (int i = 0; i < sizes[0]; i++) {
                User u = new User();

                String[] data = scan.nextLine().split(", ");
                int id = Integer.parseInt(data[0]);
                u.nome = data[1];
                u.tipo = data[2];

                user_st.put(id, u);
            }

            //Leitura da Regiao
            sizes[1] = scan.nextInt();
            scan.nextLine();

            for (int i = 0; i < sizes[1]; i++) {
                Regiao reg = new Regiao();

                String[] data = scan.nextLine().split(", ");
                reg.nome = data[0];
                reg.n_caches = Integer.parseInt(data[1]);


                // Leitura da Geocache
                for (int j = 0; j < reg.n_caches; j++) {
                    sizes[2]++;
                    Geocache geo = new Geocache();

                    String[] data1 = scan.nextLine().split(", ");
                    geo.id = data1[0];
                    int idgeo = Integer.parseInt(geo.id.replace("geocache", ""));
                    //System.out.println(idgeo);
                    geo.tipo = data1[1];
                    geo.coordenadasX = Float.parseFloat(data1[2]);
                    geo.coordenadasY = Float.parseFloat(data1[3]);
                    geo.n_itens = Integer.parseInt(data1[4]);
                    geo.id_reg = i + 1;

                    // Leitura dos itens
                    for (int k = 1; k <= geo.n_itens; k++) {
                        Item item = new Item();

                        item.item = data1[4 + k];
                        item.id_geo = geo.id;
                        item_st.put(sizes[3] + 1, item);
                        sizes[3]++;
                    }
                    geo_st.put(idgeo, geo);
                }
                reg_st.put(i + 1, reg);
            }

            // Leitura das ligacoes
            sizes[5] = scan.nextInt();
            scan.nextLine();

            for (int i = 0; i < sizes[5]; i++) {
                Ligacoes l = new Ligacoes();

                String[] data = scan.nextLine().split(", ");
                l.id_1 = data[0];
                l.id_2 = data[1];
                l.distancia = Float.parseFloat(data[2]);
                l.tempo = Integer.parseInt(data[3]);

                lig_st.put(i + 1, l);
            }

            // Leitura das travelBugs
            sizes[4] = scan.nextInt();
            scan.nextLine();

            for (int i = 0; i < sizes[4]; i++) {
                Travelbug tb = new Travelbug();

                String[] data = scan.nextLine().split(", ");
                tb.id = data[0];
                tb.user = data[1];
                tb.geo_inicial = data[2];
                tb.geo_destino = data[3];

                tvb_st.put(i + 1, tb);
            }

            // Leitura do Historico de visitas
            scan = new Scanner(new BufferedReader(new FileReader("data/logs.txt")));
            sizes[6] = scan.nextInt();
            scan.nextLine();

            for (int i = 1; i <= sizes[6]; i++) {
                HistoricoVisited histV = new HistoricoVisited();
                String[] data = scan.nextLine().split(", ");
                histV.user = data[0];
                histV.n_visited = Integer.parseInt(data[1]);
                histV.visited = new int[histV.n_visited];
                histV.date = new Date[histV.n_visited];

                for (int y = 0; y < histV.n_visited; y++) {
                    histV.visited[y] = Integer.parseInt(data[y + 2]);
                }

                // Data
                String[] data1 = scan.nextLine().split(", ");
                for (int y = 0; y < histV.n_visited; y++) {
                    String[] aux = data1[y].split("/");
                    histV.date[y] = new Date(Integer.parseInt(aux[0]), Integer.parseInt(aux[1]), Integer.parseInt(aux[2]));
                    //System.out.println(y + "   " + hist.date[y]);
                }
                hisV_st.put(i, histV);
            }


            // Leitura do historico de TB
            sizes[7] = scan.nextInt();
            scan.nextLine();
            for (int z = 1; z <= sizes[7]; z++) {
                HistoricoTB histTB = new HistoricoTB();
                String[] data2 = scan.nextLine().split(", ");
                histTB.user = data2[0];
                histTB.id_tb = Integer.parseInt(data2[1]);
                histTB.tb_start = Integer.parseInt(data2[2]);
                histTB.tb_end = Integer.parseInt(data2[3]);
                hisTB_st.put(z, histTB);
            }


        } catch (FileNotFoundException erro) {
            System.out.println(erro);
        }
        // Fim da leitura dos ficheiros

        // TESTES - INSERIR, REMOVER E EDITAR
        // USERS
        /*User user = new User();
        user.removeUser(1, sizes, user_st);
        user.addUser(1, "Patricia", "admin", sizes, user_st);
        user.addUser(8, "Patricia", "admin", sizes, user_st);
        user.removeUser(1, sizes, user_st);
        user.removeUser(1, sizes, user_st);
        user.editUser("nome", "TESTE", 1, user_st);
        user.editUser("nome", "TESTE", 2, user_st);
        System.out.println();*/

        // REGIAO
        /*Regiao regiao = new Regiao();
        regiao.removeRegiao(1, sizes, reg_st, geo_st, item_st,hisV_st);
        regiao.addRegiao(1, "TesteReg", sizes, reg_st);
        regiao.addRegiao(4, "TesteReg", sizes, reg_st);
        regiao.removeRegiao(1, sizes, reg_st, geo_st, item_st,hisV_st);
        regiao.removeRegiao(1, sizes, reg_st, geo_st, item_st,hisV_st);
        regiao.editRegiao("nome", "TESTE", 1, reg_st);
        regiao.editRegiao("nome", "TESTE", 2, reg_st);
        System.out.println();*/

        // GEOCACHE
        /*Geocache geocache = new Geocache();
        geocache.addGeocache("geocache1", "premium", -2.07543f, 43.87543f, 1, sizes, geo_st, reg_st);
        geocache.addGeocache("geocache19", "premium", -2.07543f, 43.87543f, 1, sizes, geo_st, reg_st);
        geocache.removeGeocache("geocache1", sizes, geo_st, reg_st, item_st,hisV_st);*/
        //geocache.removeGeocache("geocache1", sizes, geo_st, reg_st, item_st,hisV_st);
        /*geocache.editCache("tipo", "TESTE", 1, geo_st);
        geocache.editCache("tipo", "TESTE", 1, geo_st);
        System.out.println();*/

        // ITEM
        /*Item item = new Item();
        item.removeItem(1, sizes, item_st, geo_st);
        item.addItem(1,"geocache1", "mp3", sizes, item_st, geo_st);
        item.addItem(19,"geocache19", "mp3", sizes, item_st, geo_st);
        item.removeItem(1, sizes, item_st, geo_st);
        item.removeItem(1, sizes, item_st, geo_st);
        item.editItem("item", "TESTE", 1,item_st);
        item.editItem("item", "TESTE", 2,item_st);
        System.out.println();*/

        // LIGACOES
        /*Ligacoes ligacao = new Ligacoes();
        ligacao.removeLigacao("geocache1", "geocache2", sizes, lig_st);
        ligacao.addLigacao("geocache1", "geocache2", 599.6f, 4573, sizes, lig_st);
        ligacao.addLigacao("geocache1", "geocache18", 599.6f, 4573, sizes, lig_st);
        ligacao.removeLigacao("geocache1", "geocache2", sizes, lig_st);
        ligacao.removeLigacao("geocache1", "geocache2", sizes, lig_st);
        ligacao.editLigacao("id1", "TESTE", 1, lig_st);
        ligacao.editLigacao("id1", "TESTE", 2, lig_st);
        System.out.println();*/

        // TRAVELBUG
        /*Travelbug travelbug = new Travelbug();
        travelbug.removeTravelBug(1, sizes, tvb_st);
        travelbug.addTravelbug("travelbug1", "Pedro", "geocache12", "geocache13",sizes, tvb_st);
        travelbug.removeTravelBug(1, sizes, tvb_st);*/

        // Listar tudo
        //listarUsers(sizes, user_st);
        //listarRegiao(sizes, reg_st, geo_st, item_st);
        //listarGeocache(sizes, geo_st, item_st);
        //listarItens(sizes, item_st);
        //listarTravelbug(sizes, tvb_st);
        //listarLigacoes(sizes, lig_st);

        // Pesquisas
        /*user.searchVisitedCacheGlobal("Pedro", sizes,geo_st, reg_st, hisV_st);
        user.searchVisitedCacheRegiao("Pedro", 3, sizes,geo_st, reg_st, hisV_st);
        user.searchMissingCacheGlobal("Pedro", sizes,geo_st, reg_st, hisV_st);
        user.searchMissingCacheRegiao("Pedro",2, sizes,geo_st, reg_st, hisV_st);*/
        //geocache.usersVisitedCache("geocache1", sizes, user_st, geo_st, hisV_st);
        //geocache.premiumWithItem(sizes, geo_st);

        // Save
        //output(sizes, user_st, reg_st, geo_st, item_st, lig_st, tvb_st, hisV_st, hisTB_st);


        //new CriacaoGrafo(sizes[2], geo_st);
        CriacaoGrafo gG;
        gG = new CriacaoGrafo(sizes[2], geo_st);
        new Controller(sizes[2],geo_st,gG);

    }

    /**
     * Verifica se uma geocache contem um item
     * @param id_item - Id do Item
     * @param id_geo - Id da Geocache
     * @param itens - ST dos Itens
     * @return - Verdadeiro se contem, falso se nao contem
     */
    public static boolean geoContainsItem(int id_item, String id_geo, SequentialSearchST<Integer, Item> itens){
        if(itens.contains(id_item)){
            return (itens.get(id_item).id_geo.equals(id_geo));
        }
        return false;
    }

    /**
     * Verifica se uma regiao contem a geocache
     * @param id_geo - Id da Geocache
     * @param regiao - Id da Regiao
     * @param geo - ST das Geocaches
     * @return - Verdadeiro se contem, falso se nao contem
     */
    public static boolean regContainsCache(String id_geo, int regiao, SequentialSearchST<Integer, Geocache> geo){
        int n_geo = Integer.parseInt(id_geo.replace("geocache", ""));
        if(geo.contains(n_geo)){
            return (geo.get(n_geo).id_reg == regiao);
        }
        return false;
    }

    /**
     * Lista os users todos
     * @param sizes - Array com o numero total de Users
     * @param user_st - ST dos Users
     */
    public static void listarUsers(int[] sizes, SequentialSearchST<Integer, User> user_st) {
        int n_users = sizes[0];
        System.out.print("\n");
        for( int i = 0; i < n_users; i++){
            if (user_st.get(i+1) != null) System.out.println(i+1 + " " + user_st.get(i+1));
            else n_users++;
        }
        //System.out.print("\n");
    }

    /**
     * Lista todas as geocaches e os seus itens
     * @param sizes - Array com o numero total de Geocaches
     * @param geo_st - ST das Geocaches
     * @param item_st - ST dos Itens
     */
    public static void listarGeocache(int[] sizes, SequentialSearchST<Integer, Geocache> geo_st, SequentialSearchST<Integer, Item> item_st){
        int n_geo = sizes[2];
        System.out.print("\n");
        for( int i = 0; i < n_geo; i++){
            if (geo_st.get(i+1) != null){
                System.out.println(i+1 + " " + geo_st.get(i+1));
                int n_itens = 0;
                if(geo_st.get(i+1).n_itens > 0){ System.out.print("\tItens{");
                for(int id_item = 1; n_itens < geo_st.get(i+1).getN_itens(); id_item++){
                    if(geoContainsItem(id_item, geo_st.get(i+1).id, item_st)){
                        System.out.print(" " + item_st.get(id_item).item);
                        n_itens++;
                        if (n_itens + 1 <= geo_st.get(i+1).getN_itens()) System.out.print(",");
                    }
                }
                    System.out.print("}\n");
                }
            }
            else n_geo++;
        }
        //System.out.print("\n");
    }

    /**
     * Lista todas as ligacoes entre geocaches
     * @param sizes - Array com o numero total de ligacoes
     * @param lig_st - ST das Ligacoes
     */
    public static void listarLigacoes(int[] sizes, SequentialSearchST<Integer, Ligacoes> lig_st){
        int n_lig = sizes[5];
        System.out.println("\n");
        for(int i = 0; i < n_lig; i++){
            if(lig_st.get(i+1) != null) System.out.println(i+1 + " " + lig_st.get(i+1));
            else n_lig++;
        }
        //System.out.println("\n");
    }

    /**
     * Lista todos os itens
     * @param sizes - Array com o numero total de itens
     * @param item_st - ST dos Itens
     */
    public static void listarItens(int[] sizes, SequentialSearchST<Integer, Item> item_st){
        int n_itens = sizes[3];
        System.out.println("\n");
        for(int i = 0; i < n_itens; i++){
            if(item_st.get(i+1) != null) System.out.println(i+1 + " " + item_st.get(i+1));
            else n_itens++;
        }
        //System.out.println("\n");
    }

    /**
     * Lista todoso os travelbugs
     * @param sizes - Array com o numero total de travelbugs
     * @param tvb_st - ST dos Travelbugs
     */
    public static void listarTravelbug(int[] sizes, SequentialSearchST<Integer, Travelbug> tvb_st){
        int n_tv = sizes[4];
        System.out.println("\n");
        for(int i = 0; i < n_tv; i++){
            if(tvb_st.get(i+1) != null) System.out.println(i+1 + " " + tvb_st.get(i+1));
            else n_tv++;
        }
        //System.out.println("\n");
    }

    /**
     * Lista todas as regioes, com as suas geocaches e os respetivos itens
     * @param sizes - Array com o numero total de regios
     * @param reg_st - ST das Regioes
     * @param geo_st - St das Geocaches
     * @param item_st - St dos Itens
     */
    public static void listarRegiao(int[] sizes, SequentialSearchST<Integer, Regiao> reg_st, SequentialSearchST<Integer, Geocache> geo_st, SequentialSearchST<Integer, Item> item_st) {
        int n_reg = sizes[1];
        System.out.print("\n");
        for (int j = 1; j <= n_reg; j++) {
            if (reg_st.get(j) != null) {
                System.out.println(reg_st.get(j).nome.toUpperCase());
                int n_caches = reg_st.get(j).n_caches;
                for (int i = 1; i <= n_caches; i++) {
                    if (geo_st.get(i) != null && regContainsCache(geo_st.get(i).id, j, geo_st)) {
                        System.out.println("\t" + i + " " + geo_st.get(i));
                        int n_itens = 0;
                        if (geo_st.get(i).n_itens > 0) {
                            System.out.print("\t\tItens{");
                            for (int id_item = 1; n_itens < geo_st.get(i).getN_itens(); id_item++) {
                                if (geoContainsItem(id_item, geo_st.get(i).id, item_st)) {
                                    System.out.print(item_st.get(id_item).item);
                                    n_itens++;
                                    if (n_itens + 1 <= geo_st.get(i).getN_itens()) System.out.print(", ");
                                }
                            }
                            System.out.print("}\n");
                        }
                    } else n_caches++;
                }
                System.out.print("\n");
            } else n_reg++;
        }
    }

    /**
     * Funcao que salva toda a informacao para um ficheiro
     * @param size - Array que tem o numero total de dos valores das STs
     * @param user - St dos Users
     * @param regiao - ST das Regioes
     * @param geo - ST das Geocaches
     * @param item - ST dos Itens
     * @param lig - ST das Ligacoes
     * @param tvb - ST dos Travelbugs
     * @param visited - ST dos User Logs
     * @param tb - ST dos Travelbug Logs
     */
    public static void output(int[] size, SequentialSearchST<Integer, User> user, SequentialSearchST<Integer, Regiao> regiao, SequentialSearchST<Integer, Geocache> geo, SequentialSearchST<Integer,Item> item,
                              SequentialSearchST<Integer, Ligacoes> lig, SequentialSearchST<Integer, Travelbug> tvb, RedBlackBST<Integer, HistoricoVisited> visited, SequentialSearchST<Integer, HistoricoTB> tb){

        Out out = new Out("data/output.txt");

        // Users
        out.println(user.size());
        int n_users = size[0];
        for(int i = 1; i <= n_users; i++){
            if(user.get(i) != null) {
                out.println(i + ", " + user.get(i).nome + ", " + user.get(i).tipo);
            }else n_users++;
        }

        // Regiao
        int n_reg = size[1];
        out.println(n_reg);
        int last = 0;
        for(int i = 1; i <= n_reg; i++) {
            if (regiao.get(i) != null) {
                out.println(regiao.get(i).nome + ", " + regiao.get(i).n_caches);

                //Geocaches
                int n_geo = (regiao.get(i).n_caches + last);
                for (int j = last + 1; j <= n_geo; j++) {
                    if (geo.get(j) != null && regContainsCache("geocache"+j,i,geo)) {
                        out.print(geo.get(j).id + ", " + geo.get(j).tipo + ", " + geo.get(j).coordenadasX + ", " + geo.get(j).coordenadasY + ", " + geo.get(j).n_itens);
                        if (geo.get(j).n_itens > 0) {

                            //itens
                            int n_itens = geo.get(j).n_itens;
                            for (int k = 1; k <= n_itens; k++) {
                                if (geoContainsItem(k, geo.get(j).id, item)) {
                                    out.print(", " + item.get(k).item);
                                } else n_itens++;
                            }
                        }
                        last = j;
                        out.print("\n");
                    }else n_geo++;
                }
            }else n_reg++;
        }

        int n_lig = size[5];
        out.println(lig.size());
        for(int i = 1; i <= n_lig; i++){
            if(lig.get(i) != null) out.println(lig.get(i).id_1 + ", " + lig.get(i).id_2 + ", " + lig.get(i).distancia + ", " + lig.get(i).tempo);
        }

        int n_tvb = size[4];
        out.println(tvb.size());
        for(int i = 1; i <= n_tvb; i++){
            if(tvb.get(i) != null) out.println(tvb.get(i).id + ", " + tvb.get(i).user + ", " + tvb.get(i).geo_inicial + ", " + tvb.get(i).geo_destino);
        }

        out.close();

        // Ficheiro logs
        Out logs_out = new Out("data/logs_output.txt");

        int p = size[6];
        logs_out.println(p);
        for(int i = 1; i <= p; i++){

            logs_out.print(visited.get(i).user + ", " + visited.get(i).n_visited);
            for(int z = 0; z < visited.get(i).n_visited; z++){
                logs_out.print(", " + visited.get(i).visited[z]);
            }
            logs_out.print("\n");

            // Imprimir datas
            for(int k = 0; k < visited.get(i).n_visited; k++){
                logs_out.print(visited.get(i).date[k]);
                if(k < visited.get(i).n_visited - 1){
                    logs_out.print(", ");
                }
            }
            logs_out.print("\n");
        }

        int s = size[7];
        logs_out.println(s);
        for(int i = 1; i <= s; i++){
            logs_out.println(tb.get(i).user + ", " + tb.get(i).id_tb + ", " + tb.get(i).tb_start + ", " + tb.get(i).tb_end);
        }
        logs_out.close();
    }
}
