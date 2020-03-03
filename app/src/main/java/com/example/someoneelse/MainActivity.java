package com.example.someoneelse;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSearch = (EditText) findViewById(R.id.editSearch);
        listView = (ListView) findViewById(R.id.listView);

        // 리스트를 생성한다.
        list = new ArrayList<String>();

        // 검색에 사용할 데이터을 미리 저장한다.
        settingList();

        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = new ArrayList<String>();
        arraylist.addAll(list);

        // 리스트에 연동될 아답터를 생성한다.
        adapter = new SearchAdapter(list, this);

        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter);

        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });


    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < arraylist.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }


    // 검색에 사용될 데이터를 리스트에 추가한다.
    private void settingList() {


    String str="Istres,France,Provence-Alpes-Côte d'Azur,3012647\n" +
            "Issy-les-Moulineaux,France,Île-de-France,3012649\n" +
            "Issoire,France,Auvergne-Rhône-Alpes,3012664\n" +
            "Illzach,France,Alsace-Champagne-Ardenne-Lorraine,3012829\n" +
            "Illkirch-Graffenstaden,France,Alsace-Champagne-Ardenne-Lorraine,3012834\n" +
            "Hyères,France,Provence-Alpes-Côte d'Azur,3012937\n" +
            "Houilles,France,Île-de-France,3013097\n" +
            "Hérouville-Saint-Clair,France,Normandy,3013403\n" +
            "Herblay,France,Île-de-France,3013477\n" +
            "Hénin-Beaumont,France,Nord-Pas-de-Calais-Picardie,3013525\n" +
            "Hem,France,Nord-Pas-de-Calais-Picardie,3013549\n" +
            "Hazebrouck,France,Nord-Pas-de-Calais-Picardie,3013619\n" +
            "Hayange,France,Alsace-Champagne-Ardenne-Lorraine,3013627\n" +
            "Hautmont,France,Nord-Pas-de-Calais-Picardie,3013681\n" +
            "Yutz,France,Alsace-Champagne-Ardenne-Lorraine,3013701\n" +
            "Haubourdin,France,Nord-Pas-de-Calais-Picardie,3013862\n" +
            "Halluin,France,Nord-Pas-de-Calais-Picardie,3014034\n" +
            "Haguenau,France,Alsace-Champagne-Ardenne-Lorraine,3014078\n" +
            "Guyancourt,France,Île-de-France,3014143\n" +
            "Gujan-Mestras,France,Aquitaine-Limousin-Poitou-Charentes,3014175\n" +
            "Guéret,France,Aquitaine-Limousin-Poitou-Charentes,3014383\n" +
            "Guérande,France,Pays de la Loire,3014392\n" +
            "Grigny,France,Île-de-France,3014646\n" +
            "Grenoble,France,Auvergne-Rhône-Alpes,3014728\n" +
            "Grasse,France,Provence-Alpes-Côte d'Azur,3014856\n" +
            "Grande-Synthe,France,Nord-Pas-de-Calais-Picardie,3015160\n" +
            "Gradignan,France,Aquitaine-Limousin-Poitou-Charentes,3015419\n" +
            "Goussainville,France,Île-de-France,3015490\n" +
            "Gonesse,France,Île-de-France,3015689\n" +
            "Givors,France,Auvergne-Rhône-Alpes,3015902\n" +
            "Gif-sur-Yvette,France,Île-de-France,3016078\n" +
            "Gien,France,Centre,3016097\n" +
            "Gentilly,France,Île-de-France,3016292\n" +
            "Gennevilliers,France,Île-de-France,3016321\n" +
            "Garges-lès-Gonesse,France,Île-de-France,3016621\n" +
            "Gardanne,France,Provence-Alpes-Côte d'Azur,3016667\n" +
            "Garches,France,Île-de-France,3016675\n" +
            "Gap,France,Provence-Alpes-Côte d'Azur,3016702\n" +
            "Gagny,France,Île-de-France,3016830\n" +
            "Frontignan,France,Languedoc-Roussillon-Midi-Pyrénées,3016956\n" +
            "Fresnes,France,Île-de-France,3017178\n" +
            "Fréjus,France,Provence-Alpes-Côte d'Azur,3017253\n" +
            "Franconville,France,Île-de-France,3017341\n" +
            "Fougères,France,Brittany,3017609\n" +
            "Fos-sur-Mer,France,Provence-Alpes-Côte d'Azur,3017651\n" +
            "Forbach,France,Alsace-Champagne-Ardenne-Lorraine,3017805\n" +
            "Fontenay-sous-Bois,France,Île-de-France,3017910\n" +
            "Fontenay-le-Comte,France,Pays de la Loire,3017921\n" +
            "Fontenay-aux-Roses,France,Île-de-France,3017924\n" +
            "Fontainebleau,France,Île-de-France,3018074\n" +
            "Fontaine,France,Auvergne-Rhône-Alpes,3018095\n" +
            "Floirac,France,Aquitaine-Limousin-Poitou-Charentes,3018246\n" +
            "Fleury-les-Aubrais,France,Centre,3018280\n" +
            "Flers,France,Normandy,3018339\n" +
            "Firminy,France,Auvergne-Rhône-Alpes,3018455\n" +
            "Fécamp,France,Normandy,3018794\n" +
            "Faches-Thumesnil,France,Nord-Pas-de-Calais-Picardie,3019153\n" +
            "Eysines,France,Aquitaine-Limousin-Poitou-Charentes,3019193\n" +
            "Évry,France,Île-de-France,3019256\n" +
            "Évreux,France,Normandy,3019265\n" +
            "Étampes,France,Île-de-France,3019459\n" +
            "La Baule-Escoublac,France,Pays de la Loire,3019766\n" +
            "Ermont,France,Île-de-France,3019897\n" +
            "Éragny,France,Île-de-France,3019952\n" +
            "Équeurdreville-Hainneville,France,Normandy,3019960\n" +
            "Épinay-sur-Seine,France,Île-de-France,3020020\n" +
            "Épinal,France,Alsace-Champagne-Ardenne-Lorraine,3020035\n" +
            "Épernay,France,Alsace-Champagne-Ardenne-Lorraine,3020062\n" +
            "Elbeuf,France,Normandy,3020307\n" +
            "Élancourt,France,Île-de-France,3020310\n" +
            "Écully,France,Auvergne-Rhône-Alpes,3020392\n" +
            "Échirolles,France,Auvergne-Rhône-Alpes,3020495\n" +
            "Eaubonne,France,Île-de-France,3020601\n" +
            "Dunkerque,France,Nord-Pas-de-Calais-Picardie,3020686\n" +
            "Dreux,France,Centre,3020810\n" +
            "Draveil,France,Île-de-France,3020832\n" +
            "Drancy,France,Île-de-France,3020839\n" +
            "Draguignan,France,Provence-Alpes-Côte d'Azur,3020850\n" +
            "Douarnenez,France,Brittany,3020996\n" +
            "Douai,France,Nord-Pas-de-Calais-Picardie,3021000\n" +
            "Domont,France,Île-de-France,3021150\n" +
            "Dole,France,Bourgogne-Franche-Comté,3021263\n" +
            "Dijon,France,Bourgogne-Franche-Comté,3021372\n" +
            "Digne-les-Bains,France,Provence-Alpes-Côte d'Azur,3021382\n" +
            "Dieppe,France,Normandy,3021411\n" +
            "Deuil-la-Barre,France,Île-de-France,3021516\n" +
            "Denain,France,Nord-Pas-de-Calais-Picardie,3021605\n" +
            "Décines-Charpieu,France,Auvergne-Rhône-Alpes,3021662\n" +
            "Dax,France,Aquitaine-Limousin-Poitou-Charentes,3021670\n" +
            "Dammarie-les-Lys,France,Île-de-France,3021852\n" +
            "Cugnaux,France,Languedoc-Roussillon-Midi-Pyrénées,3022151\n" +
            "Croix,France,Nord-Pas-de-Calais-Picardie,3022376\n" +
            "Créteil,France,Île-de-France,3022530\n" +
            "Crépy-en-Valois,France,Nord-Pas-de-Calais-Picardie,3022569\n" +
            "Creil,France,Nord-Pas-de-Calais-Picardie,3022610\n" +
            "Cran-Gevrier,France,Auvergne-Rhône-Alpes,3022700\n" +
            "Cournon-d’Auvergne,France,Auvergne-Rhône-Alpes,3022988\n" +
            "Courbevoie,France,Île-de-France,3023141\n" +
            "Coulommiers,France,Île-de-France,3023240\n" +
            "Couëron,France,Pays de la Loire,3023324\n" +
            "Coudekerque-Branche,France,Nord-Pas-de-Calais-Picardie,3023356\n" +
            "Cormeilles-en-Parisis,France,Île-de-France,3023645\n" +
            "Corbeil-Essonnes,France,Île-de-France,3023763\n" +
            "Conflans-Sainte-Honorine,France,Île-de-France,3023924\n" +
            "Concarneau,France,Brittany,3024035\n" +
            "Compiègne,France,Nord-Pas-de-Calais-Picardie,3024066\n" +
            "Combs-la-Ville,France,Île-de-France,3024107\n" +
            "Pontault-Combault,France,Île-de-France,3024195\n" +
            "Colomiers,France,Languedoc-Roussillon-Midi-Pyrénées,3024223\n" +
            "Colombes,France,Île-de-France,3024266\n" +
            "Colmar,France,Alsace-Champagne-Ardenne-Lorraine,3024297\n" +
            "Cognac,France,Aquitaine-Limousin-Poitou-Charentes,3024440\n" +
            "Cluses,France,Auvergne-Rhône-Alpes,3024532\n" +
            "Clichy-sous-Bois,France,Île-de-France,3024596\n" +
            "Clichy,France,Île-de-France,3024597\n" +
            "Clermont-Ferrand,France,Auvergne-Rhône-Alpes,3024635\n" +
            "Clamart,France,Île-de-France,3024783\n" +
            "Cholet,France,Pays de la Loire,3025053\n" +
            "Choisy-le-Roi,France,Île-de-France,3025055\n" +
            "Chilly-Mazarin,France,Île-de-France,3025144\n" +
            "Chevilly-Larue,France,Île-de-France,3025314\n" +
            "Cherbourg-Octeville,France,Normandy,3025466\n" +
            "Chenôve,France,Bourgogne-Franche-Comté,3025496\n" +
            "Chennevières-sur-Marne,France,Île-de-France,3025509\n" +
            "Chelles,France,Île-de-France,3025622\n" +
            "Chaville,France,Île-de-France,3025715\n" +
            "Chaumont,France,Alsace-Champagne-Ardenne-Lorraine,3025892\n" +
            "Chatou,France,Île-de-France,3026033\n" +
            "Châtillon,France,Île-de-France,3026083\n" +
            "Châtenay-Malabry,France,Île-de-France,3026108\n" +
            "Châtellerault,France,Aquitaine-Limousin-Poitou-Charentes,3026141\n" +
            "Château-Thierry,France,Nord-Pas-de-Calais-Picardie,3026194\n" +
            "Châteauroux,France,Centre,3026204\n" +
            "Châteaudun,France,Centre,3026285\n" +
            "Chartres,France,Centre,3026467\n" +
            "Charleville-Mézières,France,Alsace-Champagne-Ardenne-Lorraine,3026613\n" +
            "Charenton-le-Pont,France,Île-de-France,3026637\n" +
            "Champs-sur-Marne,France,Île-de-France,3027014\n" +
            "Champigny-sur-Marne,France,Île-de-France,3027105\n" +
            "Chambéry,France,Auvergne-Rhône-Alpes,3027422\n" +
            "Chamalières,France,Auvergne-Rhône-Alpes,3027453\n" +
            "Chalon-sur-Saône,France,Bourgogne-Franche-Comté,3027484\n" +
            "Châlons-en-Champagne,France,Alsace-Champagne-Ardenne-Lorraine,3027487\n" +
            "Challans,France,Pays de la Loire,3027513\n" +
            "Cestas,France,Aquitaine-Limousin-Poitou-Charentes,3027763\n" +
            "Cesson-Sévigné,France,Brittany,3027767\n" +
            "Cergy,France,Île-de-France,3027883\n" +
            "Cenon,France,Aquitaine-Limousin-Poitou-Charentes,3027950\n" +
            "Cavaillon,France,Provence-Alpes-Côte d'Azur,3028134\n" +
            "Castres,France,Languedoc-Roussillon-Midi-Pyrénées,3028263\n" +
            "Castelnau-le-Lez,France,Languedoc-Roussillon-Midi-Pyrénées,3028337\n" +
            "Carvin,France,Nord-Pas-de-Calais-Picardie,3028486\n" +
            "Carrières-sous-Poissy,France,Île-de-France,3028521\n" +
            "Carquefou,France,Pays de la Loire,3028535\n" +
            "Carpentras,France,Provence-Alpes-Côte d'Azur,3028542\n" +
            "Carcassonne,France,Languedoc-Roussillon-Midi-Pyrénées,3028641\n" +
            "Canteleu,France,Normandy,3028779\n" +
            "Cannes,France,Provence-Alpes-Côte d'Azur,3028808\n" +
            "Cambrai,France,Nord-Pas-de-Calais-Picardie,3029030\n" +
            "Caluire-et-Cuire,France,Auvergne-Rhône-Alpes,3029096\n" +
            "Calais,France,Nord-Pas-de-Calais-Picardie,3029162\n" +
            "Cahors,France,Languedoc-Roussillon-Midi-Pyrénées,3029213\n" +
            "Cagnes-sur-Mer,France,Provence-Alpes-Côte d'Azur,3029227\n" +
            "Caen,France,Normandy,3029241\n" +
            "Cachan,France,Île-de-France,3029276\n" +
            "Bry-sur-Marne,France,Île-de-France,3029706\n" +
            "Brunoy,France,Île-de-France,3029748\n" +
            "Bruay-la-Buissière,France,Nord-Pas-de-Calais-Picardie,3029825\n" +
            "Bron,France,Auvergne-Rhône-Alpes,3029931\n" +
            "Brive-la-Gaillarde,France,Aquitaine-Limousin-Poitou-Charentes,3029974\n" +
            "Brignoles,France,Provence-Alpes-Côte d'Azur,3030057\n" +
            "Brie-Comte-Robert,France,Île-de-France,3030101\n" +
            "Brétigny-sur-Orge,France,Île-de-France,3030257\n" +
            "Brest,France,Brittany,3030300\n" +
            "Bressuire,France,Aquitaine-Limousin-Poitou-Charentes,3030303\n" +
            "Bourgoin,France,Auvergne-Rhône-Alpes,3030960\n" +
            "Bourg-lès-Valence,France,Auvergne-Rhône-Alpes,3030985\n" +
            "Bourg-la-Reine,France,Île-de-France,3030990\n" +
            "Bourges,France,Centre,3031005\n" +
            "Bourg-en-Bresse,France,Auvergne-Rhône-Alpes,3031009\n" +
            "Boulogne-sur-Mer,France,Nord-Pas-de-Calais-Picardie,3031133\n" +
            "Boulogne-Billancourt,France,Île-de-France,3031137\n" +
            "Bouguenais,France,Pays de la Loire,3031268\n" +
            "Bordeaux,France,Aquitaine-Limousin-Poitou-Charentes,3031582\n" +
            "Bonneuil-sur-Marne,France,Île-de-France,3031709\n" +
            "Bondy,France,Île-de-France,3031815\n" +
            "Boissy-Saint-Léger,France,Île-de-France,3031898\n" +
            "Bois-Colombes,France,Île-de-France,3032070\n" +
            "Bobigny,France,Île-de-France,3032179\n" +
            "Blois,France,Centre,3032213\n" +
            "Blanquefort,France,Aquitaine-Limousin-Poitou-Charentes,3032340\n" +
            "Blagnac,France,Languedoc-Roussillon-Midi-Pyrénées,3032469\n" +
            "Bischheim,France,Alsace-Champagne-Ardenne-Lorraine,3032551\n" +
            "Biarritz,France,Aquitaine-Limousin-Poitou-Charentes,3032797\n" +
            "Bezons,France,Île-de-France,3032824\n" +
            "Béziers,France,Languedoc-Roussillon-Midi-Pyrénées,3032833\n" +
            "Béthune,France,Nord-Pas-de-Calais-Picardie,3033002\n" +
            "Besançon,France,Bourgogne-Franche-Comté,3033123\n" +
            "Bergerac,France,Aquitaine-Limousin-Poitou-Charentes,3033391\n" +
            "Berck,France,Nord-Pas-de-Calais-Picardie,3033415\n" +
            "Berck-Plage,France,Nord-Pas-de-Calais-Picardie,3033416\n" +
            "Belfort,France,Bourgogne-Franche-Comté,3033791\n" +
            "Bègles,France,Aquitaine-Limousin-Poitou-Charentes,3033881\n" +
            "Beauvais,France,Nord-Pas-de-Calais-Picardie,3034006\n" +
            "Beaune,France,Bourgogne-Franche-Comté,3034126\n" +
            "Bayonne,France,Aquitaine-Limousin-Poitou-Charentes,3034475\n" +
            "Bayeux,France,Normandy,3034483\n" +
            "Bastia,France,Corsica,3034640\n" +
            "Bar-le-Duc,France,Alsace-Champagne-Ardenne-Lorraine,3034911\n" +
            "Balma,France,Languedoc-Roussillon-Midi-Pyrénées,3035204\n" +
            "Bagnols-sur-Cèze,France,Languedoc-Roussillon-Midi-Pyrénées,3035396\n" +
            "Bagnolet,France,Île-de-France,3035403\n" +
            "Bagneux,France,Île-de-France,3035409\n" +
            "Avon,France,Île-de-France,3035654\n" +
            "Avion,France,Nord-Pas-de-Calais-Picardie,3035667\n" +
            "Avignon,France,Provence-Alpes-Côte d'Azur,3035681\n" +
            "Auxerre,France,Bourgogne-Franche-Comté,3035843\n" +
            "Autun,France,Bourgogne-Franche-Comté,3035883\n" +
            "Aurillac,France,Auvergne-Rhône-Alpes,3036016\n" +
            "Aulnay-sous-Bois,France,Île-de-France,3036145\n" +
            "Audincourt,France,Bourgogne-Franche-Comté,3036240\n" +
            "Auch,France,Languedoc-Roussillon-Midi-Pyrénées,3036281\n" +
            "Aubervilliers,France,Île-de-France,3036386\n" +
            "Aubagne,France,Provence-Alpes-Côte d'Azur,3036433\n" +
            "Athis-Mons,France,Île-de-France,3036460\n" +
            "Asnières-sur-Seine,France,Île-de-France,3036572\n" +
            "Arras,France,Nord-Pas-de-Calais-Picardie,3036784\n" +
            "Armentières,France,Nord-Pas-de-Calais-Picardie,3036903\n" +
            "Arles,France,Provence-Alpes-Côte d'Azur,3036938\n" +
            "Argenteuil,France,Île-de-France,3037044\n" +
            "Argentan,France,Normandy,3037051\n" +
            "Arcueil,France,Île-de-France,3037157\n" +
            "Antony,France,Île-de-France,3037423\n" +
            "Antibes,France,Provence-Alpes-Côte d'Azur,3037456\n" +
            "Annonay,France,Auvergne-Rhône-Alpes,3037514\n" +
            "Annemasse,France,Auvergne-Rhône-Alpes,3037538\n" +
            "Annecy-le-Vieux,France,Auvergne-Rhône-Alpes,3037540\n" +
            "Annecy,France,Auvergne-Rhône-Alpes,3037543\n" +
            "Angoulême,France,Aquitaine-Limousin-Poitou-Charentes,3037598\n" +
            "Anglet,France,Aquitaine-Limousin-Poitou-Charentes,3037612\n" +
            "Angers,France,Pays de la Loire,3037656\n" +
            "Amiens,France,Nord-Pas-de-Calais-Picardie,3037854\n" +
            "Allauch,France,Provence-Alpes-Côte d'Azur,3038159\n" +
            "Alfortville,France,Île-de-France,3038213\n" +
            "Alès,France,Languedoc-Roussillon-Midi-Pyrénées,3038224\n" +
            "Alençon,France,Normandy,3038230\n" +
            "Albi,France,Languedoc-Roussillon-Midi-Pyrénées,3038261\n" +
            "Albertville,France,Auvergne-Rhône-Alpes,3038266\n" +
            "Ajaccio,France,Corsica,3038334\n" +
            "Aix-les-Bains,France,Auvergne-Rhône-Alpes,3038350\n" +
            "Aix-en-Provence,France,Provence-Alpes-Côte d'Azur,3038354\n" +
            "Agen,France,Aquitaine-Limousin-Poitou-Charentes,3038634\n" +
            "Agde,France,Languedoc-Roussillon-Midi-Pyrénées,3038638\n" +
            "Achères,France,Île-de-France,3038712\n" +
            "Abbeville,France,Nord-Pas-de-Calais-Picardie,3038789\n" +
            "Villeneuve-d'Ascq,France,Nord-Pas-de-Calais-Picardie,6543862\n" +
            "Les Ulis,France,Île-de-France,6615536\n" +
            "Bourgoin-Jallieu,France,Auvergne-Rhône-Alpes,6615539\n" +
            "Marseille 01,France,Provence-Alpes-Côte d'Azur,7284882\n" +
            "Marseille 02,France,Provence-Alpes-Côte d'Azur,7284883\n" +
            "Marseille 03,France,Provence-Alpes-Côte d'Azur,7284884\n" +
            "Marseille 04,France,Provence-Alpes-Côte d'Azur,7284885\n" +
            "Marseille 05,France,Provence-Alpes-Côte d'Azur,7284886\n" +
            "Marseille 06,France,Provence-Alpes-Côte d'Azur,7284887\n" +
            "Marseille 07,France,Provence-Alpes-Côte d'Azur,7284888\n" +
            "Marseille 08,France,Provence-Alpes-Côte d'Azur,7284889\n" +
            "Marseille 10,France,Provence-Alpes-Côte d'Azur,7284890\n" +
            "Marseille 09,France,Provence-Alpes-Côte d'Azur,7284891\n" +
            "Marseille 11,France,Provence-Alpes-Côte d'Azur,7284892\n" +
            "Marseille 12,France,Provence-Alpes-Côte d'Azur,7284893\n" +
            "Marseille 13,France,Provence-Alpes-Côte d'Azur,7284894\n" +
            "Marseille 14,France,Provence-Alpes-Côte d'Azur,7284895\n" +
            "Marseille 15,France,Provence-Alpes-Côte d'Azur,7284896\n" +
            "Marseille 16,France,Provence-Alpes-Côte d'Azur,7284897\n" +
            "La Defense,France,Île-de-France,8504417\n" +
            "Saint-Quentin-en-Yvelines,France,Île-de-France,8533870\n" +
            "Cergy-Pontoise,France,Île-de-France,8555643\n" +
            "Tchibanga,Gabon,Nyanga,2396253\n" +
            "Port-Gentil,Gabon,Ogooué-Maritime,2396518\n" +
            "Oyem,Gabon,Woleu-Ntem,2396646\n" +
            "Mouila,Gabon,Ngounié,2398073\n" +
            "Moanda,Gabon,Haut-Ogooué,2398269\n" +
            "Libreville,Gabon,Estuaire,2399697\n" +
            "Lambaréné,Gabon,Moyen-Ogooué,2399888\n" +
            "Koulamoutou,Gabon,Ogooué-Lolo,2399959\n" +
            "Franceville,Gabon,Haut-Ogooué,2400555\n" +
            "York,United Kingdom,England,2633352\n" +
            "Yeovil,United Kingdom,England,2633373\n" +
            "Yeadon,United Kingdom,England,2633397\n" +
            "Yate,United Kingdom,England,2633406\n" +
            "Wrexham,United Kingdom,Wales,2633485\n" +
            "Worthing,United Kingdom,England,2633521\n" +
            "Worksop,United Kingdom,England,2633551\n" +
            "Workington,United Kingdom,England,2633553\n" +
            "Worcester,United Kingdom,England,2633563\n" +
            "Woodford Green,United Kingdom,England,2633655\n" +
            "Wombwell,United Kingdom,England,2633681\n" +
            "Wolverhampton,United Kingdom,England,2633691\n" +
            "Wokingham,United Kingdom,England,2633708\n" +
            "Woking,United Kingdom,England,2633709\n" +
            "Witney,United Kingdom,England,2633729\n" +
            "Witham,United Kingdom,England,2633749\n" +
            "Wishaw,United Kingdom,Scotland,2633765\n" +
            "Wisbech,United Kingdom,England,2633771\n" +
            "Winsford,United Kingdom,England,2633810\n" +
            "Windsor,United Kingdom,England,2633842\n" +
            "Winchester,United Kingdom,England,2633858\n" +
            "Wilmslow,United Kingdom,England,2633883\n" +
            "Willenhall,United Kingdom,England,2633912\n" +
            "Wigston Magna,United Kingdom,England,2633936\n" +
            "Wigan,United Kingdom,England,2633948\n" +
            "Widnes,United Kingdom,England,2633954\n" +
            "Wickford,United Kingdom,England,2633976\n" +
            "Whitstable,United Kingdom,England,2634021\n" +
            "Whitley Bay,United Kingdom,England,2634032\n" +
            "Whitehaven,United Kingdom,England,2634096\n" +
            "Whitefield,United Kingdom,England,2634103\n" +
            "Whickham,United Kingdom,England,2634155\n" +
            "Weymouth,United Kingdom,England,2634202\n" +
            "Weybridge,United Kingdom,England,2634204\n" +
            "Weston-super-Mare,United Kingdom,England,2634308\n" +
            "West Molesey,United Kingdom,England,2634340\n" +
            "Westhoughton,United Kingdom,England,2634387\n" +
            "West Bromwich,United Kingdom,England,2634491\n" +
            "West Bridgford,United Kingdom,England,2634493\n" +
            "Welwyn Garden City,United Kingdom,England,2634552\n" +
            "Wellington,United Kingdom,England,2634573\n" +
            "Wellingborough,United Kingdom,England,2634578\n" +
            "Welling,United Kingdom,England,2634579\n" +
            "Wednesfield,United Kingdom,England,2634616\n" +
            "Wednesbury,United Kingdom,England,2634617\n" +
            "Wath upon Dearne,United Kingdom,England,2634672\n" +
            "Watford,United Kingdom,England,2634677\n" +
            "Waterlooville,United Kingdom,England,2634686\n" +
            "Washington,United Kingdom,England,2634715\n" +
            "Warwick,United Kingdom,England,2634725\n" +
            "Warrington,United Kingdom,England,2634739\n" +
            "Warminster,United Kingdom,England,2634755\n" +
            "Ware,United Kingdom,England,2634777\n" +
            "Walton-on-Thames,United Kingdom,England,2634825\n" +
            "Waltham Abbey,United Kingdom,England,2634843\n" +
            "Walsall,United Kingdom,England,2634853\n" +
            "Wallsend,United Kingdom,England,2634864\n" +
            "Wallasey,United Kingdom,England,2634873\n" +
            "Walkden,United Kingdom,England,2634887\n" +
            "Wakefield,United Kingdom,England,2634910\n" +
            "Urmston,United Kingdom,England,2635062\n" +
            "Uckfield,United Kingdom,England,2635243\n" +
            "Stowmarket,United Kingdom,England,2636749\n" +
            "Stourport-on-Severn,United Kingdom,England,2636767\n" +
            "Stourbridge,United Kingdom,England,2636769\n" +
            "Stoke-on-Trent,United Kingdom,England,2636841\n" +
            "Stockton-on-Tees,United Kingdom,England,2636876\n" +
            "Stockport,United Kingdom,England,2636882\n" +
            "Stirling,United Kingdom,Scotland,2636910\n" +
            "Stevenage,United Kingdom,England,2636940\n" +
            "Staveley,United Kingdom,England,2636995\n" +
            "Stamford,United Kingdom,England,2637104\n" +
            "Stalybridge,United Kingdom,England,2637106\n" +
            "Staines,United Kingdom,England,2637126\n" +
            "Stafford,United Kingdom,England,2637142\n" +
            "Spennymoor,United Kingdom,England,2637235\n" +
            "Spalding,United Kingdom,England,2637265\n" +
            "South Shields,United Kingdom,England,2637329\n" +
            "Southsea,United Kingdom,England,2637330\n" +
            "Southport,United Kingdom,England,2637343\n" +
            "South Ockendon,United Kingdom,England,2637355\n" +
            "Southend-on-Sea,United Kingdom,England,2637433\n" +
            "South Elmsall,United Kingdom,England,2637435\n" +
            "South Benfleet,United Kingdom,England,2637476\n" +
            "Southampton,United Kingdom,England,2637487\n" +
            "Southall,United Kingdom,England,2637490\n" +
            "Solihull,United Kingdom,England,2637546\n" +
            "Slough,United Kingdom,England,2637627\n" +
            "Sleaford,United Kingdom,England,2637659\n" +
            "Skelmersdale,United Kingdom,England,2637752\n" +
            "Skegness,United Kingdom,England,2637762\n" +
            "Sittingbourne,United Kingdom,England,2637802\n" +
            "Shrewsbury,United Kingdom,England,2637891\n" +
            "Shoreham-by-Sea,United Kingdom,England,2637916\n" +
            "Shoreham,United Kingdom,England,2637917\n" +
            "Shipley,United Kingdom,England,2637958\n" +
            "Sheffield,United Kingdom,England,2638077\n" +
            "Sevenoaks,United Kingdom,England,2638187\n" +
            "Selby,United Kingdom,England,2638235\n" +
            "Seaham,United Kingdom,England,2638302\n" +
            "Seaford,United Kingdom,England,2638311\n" +
            "Scunthorpe,United Kingdom,England,2638324\n" +
            "Scarborough,United Kingdom,England,2638419\n" +
            "Sandown,United Kingdom,England,2638568\n" +
            "Sandbach,United Kingdom,England,2638600\n" +
            "Salisbury,United Kingdom,England,2638664\n" +
            "Salford,United Kingdom,England,2638671\n" +
            "Sale,United Kingdom,England,2638678\n" +
            "Saint Neots,United Kingdom,England,2638717\n" +
            "St Helens,United Kingdom,England,2638785\n" +
            "St Austell,United Kingdom,England,2638853\n" +
            "Saint Andrews,United Kingdom,Scotland,2638864\n" +
            "St Albans,United Kingdom,England,2638867\n" +
            "Ryton,United Kingdom,England,2638894\n" +
            "Ryde,United Kingdom,England,2638911\n" +
            "Rutherglen,United Kingdom,Scotland,2638926\n" +
            "Rushden,United Kingdom,England,2638946\n" +
            "Runcorn,United Kingdom,England,2638960\n" +
            "Ruislip,United Kingdom,England,2638976\n" +
            "Rugeley,United Kingdom,England,2638977\n" +
            "Rugby,United Kingdom,England,2638978\n" +
            "Royton,United Kingdom,England,2639015\n" +
            "Royal Tunbridge Wells,United Kingdom,England,2639022\n" +
            "Rottingdean,United Kingdom,England,2639069\n" +
            "Rotherham,United Kingdom,England,2639093\n" +
            "Romsey,United Kingdom,England,2639189\n" +
            "Rochford,United Kingdom,England,2639265\n" +
            "Rochester,United Kingdom,England,2639268\n" +
            "Rochdale,United Kingdom,England,2639272\n" +
            "Risca,United Kingdom,Wales,2639317\n" +
            "Ripon,United Kingdom,England,2639323\n" +
            "Ripley,United Kingdom,England,2639325\n" +
            "Rhyl,United Kingdom,Wales,2639409\n" +
            "Rhondda,United Kingdom,Wales,2639447\n" +
            "Renfrew,United Kingdom,Scotland,2639495\n" +
            "Reigate,United Kingdom,England,2639506\n" +
            "Redhill,United Kingdom,England,2639545\n" +
            "Redditch,United Kingdom,England,2639557\n" +
            "Redcar,United Kingdom,England,2639563\n" +
            "Reading,United Kingdom,England,2639577\n" +
            "Rayleigh,United Kingdom,England,2639583\n" +
            "Rawtenstall,United Kingdom,England,2639586\n" +
            "Rawmarsh,United Kingdom,England,2639588\n" +
            "Ramsgate,United Kingdom,England,2639660\n" +
            "Ramsbottom,United Kingdom,England,2639668\n" +
            "Purley,United Kingdom,England,2639842\n" +
            "Pudsey,United Kingdom,England,2639866\n" +
            "Prestwick,United Kingdom,Scotland,2639896\n" +
            "Prestwich,United Kingdom,England,2639897\n" +
            "Preston,United Kingdom,England,2639912\n" +
            "Prestatyn,United Kingdom,Wales,2639926\n" +
            "Prescot,United Kingdom,England,2639928\n" +
            "Poulton le Fylde,United Kingdom,England,2639958\n" +
            "Potters Bar,United Kingdom,England,2639970\n" +
            "Portsmouth,United Kingdom,England,2639996\n" +
            "Portslade,United Kingdom,England,2639998\n" +
            "Portishead,United Kingdom,England,2640037\n" +
            "Porthcawl,United Kingdom,Wales,2640054\n" +
            "Port Glasgow,United Kingdom,Scotland,2640060\n" +
            "Portadown,United Kingdom,Northern Ireland,2640085\n" +
            "Poole,United Kingdom,England,2640101\n" +
            "Pontypridd,United Kingdom,Wales,2640104\n" +
            "Pontypool,United Kingdom,Wales,2640106\n" +
            "Pontefract,United Kingdom,England,2640132\n" +
            "Polmont,United Kingdom,Scotland,2640155\n" +
            "Plymstock,United Kingdom,England,2640190\n" +
            "Plymouth,United Kingdom,England,2640194\n" +
            "Pitsea,United Kingdom,England,2640246\n" +
            "Pinner,United Kingdom,England,2640275\n" +
            "Peterlee,United Kingdom,England,2640349\n" +
            "Peterhead,United Kingdom,Scotland,2640351\n" +
            "Peterborough,United Kingdom,England,2640354\n" +
            "Perth,United Kingdom,Scotland,2640358\n" +
            "Penzance,United Kingdom,England,2640377\n" +
            "Penicuik,United Kingdom,Scotland,2640465\n" +
            "Penarth,United Kingdom,Wales,2640496\n" +
            "Paisley,United Kingdom,Scotland,2640677\n" +
            "Paignton,United Kingdom,England,2640681\n" +
            "Oxford,United Kingdom,England,2640729\n" +
            "Oswestry,United Kingdom,England,2640861\n" +
            "Ossett,United Kingdom,England,2640869\n" +
            "Orpington,United Kingdom,England,2640894\n" +
            "Ormskirk,United Kingdom,England,2640908\n" +
            "Omagh,United Kingdom,Northern Ireland,2640967\n" +
            "Oldham,United Kingdom,England,2641022\n" +
            "Oadby,United Kingdom,England,2641134\n" +
            "Nuneaton,United Kingdom,England,2641157\n" +
            "Nottingham,United Kingdom,England,2641170\n" +
            "Norwich,United Kingdom,England,2641181\n" +
            "Northwich,United Kingdom,England,2641224\n" +
            "North Shields,United Kingdom,England,2641267\n" +
            "Northolt,United Kingdom,England,2641290\n" +
            "Lancing,United Kingdom,England,2641319\n" +
            "Northampton,United Kingdom,England,2641430\n" +
            "Northallerton,United Kingdom,England,2641435\n" +
            "Newtownards,United Kingdom,Northern Ireland,2641519\n" +
            "Newtownabbey,United Kingdom,Northern Ireland,2641520\n" +
            "Newton Mearns,United Kingdom,Scotland,2641544\n" +
            "Newton-le-Willows,United Kingdom,England,2641546\n" +
            "Newton Aycliffe,United Kingdom,England,2641555\n" +
            "Newton Abbot,United Kingdom,England,2641557\n" +
            "Newry,United Kingdom,Northern Ireland,2641581\n" +
            "Newquay,United Kingdom,England,2641589\n" +
            "Newport Pagnell,United Kingdom,England,2641591\n" +
            "Newport,United Kingdom,Wales,2641598\n" +
            "Newport,United Kingdom,England,2641599\n" +
            "New Milton,United Kingdom,England,2641609\n" +
            "Newmarket,United Kingdom,England,2641616\n" +
            "New Malden,United Kingdom,England,2641617\n" +
            "Newcastle upon Tyne,United Kingdom,England,2641673\n" +
            "Newcastle under Lyme,United Kingdom,England,2641674\n" +
            "Newbury,United Kingdom,England,2641689\n" +
            "Newburn,United Kingdom,England,2641690\n" +
            "Newark on Trent,United Kingdom,England,2641731\n" +
            "Nelson,United Kingdom,England,2641810\n" +
            "Neath,United Kingdom,Wales,2641843\n" +
            "Nailsea,United Kingdom,England,2641913\n" +
            "Musselburgh,United Kingdom,Scotland,2641942\n" +
            "Motherwell,United Kingdom,Scotland,2642135\n" +
            "Morley,United Kingdom,England,2642189\n" +
            "Moreton,United Kingdom,England,2642204\n" +
            "Morecambe,United Kingdom,England,2642214\n" +
            "Mitcham,United Kingdom,England,2642414\n" +
            "Mirfield,United Kingdom,England,2642423\n" +
            "Milton Keynes,United Kingdom,England,2642465\n" +
            "Middleton,United Kingdom,England,2642593\n" +
            "Middlesbrough,United Kingdom,England,2642607\n" +
            "Mexborough,United Kingdom,England,2642683\n" +
            "Merthyr Tydfil,United Kingdom,Wales,2642705\n" +
            "Melton Mowbray,United Kingdom,England,2642763\n" +
            "Marple,United Kingdom,England,2642999\n" +
            "Marlow,United Kingdom,England,2643003\n" +
            "Market Harborough,United Kingdom,England,2643027\n" +
            "Margate,United Kingdom,England,2643044\n" +
            "March,United Kingdom,England,2643071\n" +
            "Mansfield Woodhouse,United Kingdom,England,2643096\n" +
            "Mansfield,United Kingdom,England,2643097\n" +
            "Mangotsfield,United Kingdom,England,2643116\n" +
            "Manchester,United Kingdom,England,2643123\n" +
            "Maltby,United Kingdom,England,2643144\n" +
            "Maldon,United Kingdom,England,2643160\n" +
            "Maidstone,United Kingdom,England,2643179\n" +
            "Maidenhead,United Kingdom,England,2643186\n" +
            "Maghull,United Kingdom,England,2643198\n" +
            "Maesteg,United Kingdom,Wales,2643218\n" +
            "Macclesfield,United Kingdom,England,2643266\n" +
            "Luton,United Kingdom,England,2643339\n" +
            "Lowestoft,United Kingdom,England,2643490\n" +
            "Louth,United Kingdom,England,2643553\n" +
            "Loughborough,United Kingdom,England,2643567\n" +
            "Longfield,United Kingdom,England,2643696\n" +
            "Long Eaton,United Kingdom,England,2643697\n" +
            "Londonderry County Borough,United Kingdom,Northern Ireland,2643734\n" +
            "Derry,United Kingdom,Northern Ireland,2643736\n" +
            "City of London,United Kingdom,England,2643741\n" +
            "London,United Kingdom,England,2643743\n" +
            "Lofthouse,United Kingdom,England,2643776\n" +
            "Llanelli,United Kingdom,Wales,2644100\n" +
            "Llandudno,United Kingdom,Wales,2644120\n" +
            "Livingston,United Kingdom,Scotland,2644204\n" +
            "Liverpool,United Kingdom,England,2644210\n" +
            "Littlehampton,United Kingdom,England,2644319\n" +
            "Litherland,United Kingdom,England,2644386\n" +
            "Lisburn,United Kingdom,Northern Ireland,2644411\n" +
            "Lincoln,United Kingdom,England,2644487\n" +
            "Lichfield,United Kingdom,England,2644531\n" +
            "Leyland,United Kingdom,England,2644547\n" +
            "Lewes,United Kingdom,England,2644559\n" +
            "Letchworth,United Kingdom,England,2644597\n" +
            "Leighton Buzzard,United Kingdom,England,2644652\n" +
            "Leigh,United Kingdom,England,2644660\n" +
            "Leicester,United Kingdom,England,2644668\n" +
            "Leek,United Kingdom,England,2644684\n" +
            "Leeds,United Kingdom,England,2644688\n" +
            "Leatherhead,United Kingdom,England,2644726\n" +
            "Royal Leamington Spa,United Kingdom,England,2644737\n" +
            "Larne,United Kingdom,Northern Ireland,2644849\n" +
            "Larkhall,United Kingdom,Scotland,2644853\n" +
            "Lancaster,United Kingdom,England,2644972\n" +
            "Kirkintilloch,United Kingdom,Scotland,2645261\n" +
            "Kirkcaldy,United Kingdom,Scotland,2645298\n" +
            "Kirkby in Ashfield,United Kingdom,England,2645309\n" +
            "Kirkby,United Kingdom,England,2645313\n" +
            "Kingswood,United Kingdom,England,2645418\n" +
            "Kingswinford,United Kingdom,England,2645420\n" +
            "Hull,United Kingdom,England,2645425\n" +
            "King's Lynn,United Kingdom,England,2645456\n" +
            "Kilwinning,United Kingdom,Scotland,2645541\n" +
            "Kilmarnock,United Kingdom,Scotland,2645605\n" +
            "Kidsgrove,United Kingdom,England,2645721\n" +
            "Kidlington,United Kingdom,England,2645722\n" +
            "Kidderminster,United Kingdom,England,2645724\n" +
            "Keynsham,United Kingdom,England,2645733\n" +
            "Kettering,United Kingdom,England,2645753\n" +
            "Kenilworth,United Kingdom,England,2645822\n" +
            "Kendal,United Kingdom,England,2645826\n" +
            "Kempston,United Kingdom,England,2645831\n" +
            "Keighley,United Kingdom,England,2645889\n" +
            "Johnstone,United Kingdom,Scotland,2645951\n" +
            "Jarrow,United Kingdom,England,2645972\n" +
            "Islington,United Kingdom,England,2646003\n" +
            "Isleworth,United Kingdom,England,2646004\n" +
            "Irvine,United Kingdom,England,2646032\n" +
            "Coity,United Kingdom,Wales,2652622\n" +
            "Cobham,United Kingdom,England,2652689\n" +
            "Coatbridge,United Kingdom,Scotland,2652696\n" +
            "Coalville,United Kingdom,England,2652698\n" +
            "Clydebank,United Kingdom,Scotland,2652730\n" +
            "Clydach,United Kingdom,Wales,2652734\n" +
            "Clitheroe,United Kingdom,England,2652819\n" +
            "Clevedon,United Kingdom,England,2652861\n" +
            "Cleethorpes,United Kingdom,England,2652885\n" +
            "Cleckheaton,United Kingdom,England,2652890\n" +
            "Clacton-on-Sea,United Kingdom,England,2652974\n" +
            "Cirencester,United Kingdom,England,2652995\n" +
            "Christchurch,United Kingdom,England,2653075\n" +
            "Chorley,United Kingdom,England,2653086\n" +
            "Chislehurst,United Kingdom,England,2653123\n" +
            "Chipping Sodbury,United Kingdom,England,2653137\n" +
            "Chippenham,United Kingdom,England,2653144\n" +
            "Chichester,United Kingdom,England,2653192\n" +
            "Chester-le-Street,United Kingdom,England,2653224\n" +
            "Chesterfield,United Kingdom,England,2653225\n" +
            "Chester,United Kingdom,England,2653228\n" +
            "Chessington,United Kingdom,England,2653229\n" +
            "Cheshunt,United Kingdom,England,2653232\n" +
            "Chesham,United Kingdom,England,2653235\n" +
            "Cheltenham,United Kingdom,England,2653261\n" +
            "Chelsea,United Kingdom,England,2653265\n" +
            "Chelmsford,United Kingdom,England,2653266\n" +
            "Cheadle Hulme,United Kingdom,England,2653290\n" +
            "Chatham,United Kingdom,England,2653305\n" +
            "Chapletown,United Kingdom,England,2653353\n" +
            "Chalfont Saint Peter,United Kingdom,England,2653393\n" +
            "Caterham,United Kingdom,England,2653520\n" +
            "Castlereagh,United Kingdom,Northern Ireland,2653558\n" +
            "Castleford,United Kingdom,England,2653584\n" +
            "Carshalton,United Kingdom,England,2653646\n" +
            "Carrickfergus,United Kingdom,Northern Ireland,2653680\n" +
            "Carmarthen,United Kingdom,Wales,2653755\n" +
            "Carlisle,United Kingdom,England,2653775\n" +
            "Cardiff,United Kingdom,Wales,2653822\n" +
            "Canterbury,United Kingdom,England,2653877\n" +
            "Cannock,United Kingdom,England,2653883\n" +
            "Cambridge,United Kingdom,England,2653941\n" +
            "Camborne,United Kingdom,England,2653945\n" +
            "Camberley,United Kingdom,England,2653947\n" +
            "Caerphilly,United Kingdom,Wales,2654089\n" +
            "Buxton,United Kingdom,England,2654141\n" +
            "Bushey,United Kingdom,England,2654179\n" +
            "Bury St Edmunds,United Kingdom,England,2654186\n" +
            "Bury,United Kingdom,England,2654187\n" +
            "Burton upon Trent,United Kingdom,England,2654200\n" +
            "Burntwood,United Kingdom,England,2654252\n" +
            "Burnley,United Kingdom,England,2654264\n" +
            "Burnham-on-Sea,United Kingdom,England,2654269\n" +
            "Burgess Hill,United Kingdom,England,2654308\n" +
            "Buckley,United Kingdom,Wales,2654394\n" +
            "Buckhaven,United Kingdom,Scotland,2654415\n" +
            "Brymbo,United Kingdom,Wales,2654450\n" +
            "Brownhills,United Kingdom,England,2654490\n" +
            "Bromsgrove,United Kingdom,England,2654579\n" +
            "Broadstairs,United Kingdom,England,2654635\n" +
            "Brixham,United Kingdom,England,2654663\n" +
            "Briton Ferry,United Kingdom,Wales,2654668\n" +
            "Bristol,United Kingdom,England,2654675\n" +
            "Brighton,United Kingdom,England,2654710\n" +
            "Brighouse,United Kingdom,England,2654715\n" +
            "Brierley Hill,United Kingdom,England,2654724\n" +
            "Bridlington,United Kingdom,England,2654728\n" +
            "Bridgwater,United Kingdom,England,2654730\n" +
            "Bridgend,United Kingdom,Wales,2654755\n" +
            "Brentwood,United Kingdom,England,2654782\n" +
            "Bredbury,United Kingdom,England,2654814\n" +
            "Bramhall,United Kingdom,England,2654927\n" +
            "Braintree,United Kingdom,England,2654938\n" +
            "Bradford,United Kingdom,England,2654993\n" +
            "Bracknell,United Kingdom,England,2655009\n" +
            "Bournemouth,United Kingdom,England,2655095\n" +
            "Boston,United Kingdom,England,2655138\n" +
            "Borehamwood,United Kingdom,England,2655186\n" +
            "Bootle,United Kingdom,England,2655198\n" +
            "Bolton,United Kingdom,England,2655237\n" +
            "Bognor Regis,United Kingdom,England,2655262\n" +
            "Blyth,United Kingdom,England,2655315\n" +
            "Bloxwich,United Kingdom,England,2655329\n" +
            "Bletchley,United Kingdom,England,2655351\n" +
            "Blackpool,United Kingdom,England,2655459\n" +
            "Blackburn,United Kingdom,England,2655524\n" +
            "Bishopstoke,United Kingdom,England,2655557\n" +
            "Bishops Stortford,United Kingdom,England,2655562\n" +
            "Bishopbriggs,United Kingdom,Scotland,2655582\n" +
            "Bishop Auckland,United Kingdom,England,2655583\n" +
            "Birmingham,United Kingdom,England,2655603\n" +
            "Birkenhead,United Kingdom,England,2655613\n" +
            "Bingley,United Kingdom,England,2655642\n" +
            "Billingham,United Kingdom,England,2655664\n" +
            "Billericay,United Kingdom,England,2655672\n" +
            "Biggleswade,United Kingdom,England,2655689\n" +
            "Bideford,United Kingdom,England,2655707\n" +
            "Biddulph,United Kingdom,England,2655708\n" +
            "Bicester,United Kingdom,England,2655729\n" +
            "Bexley,United Kingdom,England,2655775\n" +
            "Bexhill-on-Sea,United Kingdom,England,2655777\n" +
            "Beverley,United Kingdom,England,2655785\n" +
            "Berwick-Upon-Tweed,United Kingdom,England,2655819\n" +
            "Berkhamsted,United Kingdom,England,2655858\n" +
            "Bentley,United Kingdom,England,2655882\n" +
            "Belper,United Kingdom,England,2655942\n" +
            "Bellshill,United Kingdom,Scotland,2655952\n" +
            "Belfast,United Kingdom,Northern Ireland,2655984\n" +
            "Bedworth,United Kingdom,England,2656031\n" +
            "Bedlington,United Kingdom,England,2656039\n" +
            "Bedford,United Kingdom,England,2656046\n" +
            "Beckenham,United Kingdom,England,2656065\n" +
            "Bebington,United Kingdom,England,2656070\n" +
            "Bearsden,United Kingdom,Scotland,2656086\n" +
            "Batley,United Kingdom,England,2656168\n" +
            "Bathgate,United Kingdom,Scotland,2656169\n" +
            "Bath,United Kingdom,England,2656173\n" +
            "Basingstoke,United Kingdom,England,2656192\n" +
            "Basildon,United Kingdom,England,2656194\n" +
            "Barry,United Kingdom,Wales,2656235\n" +
            "Barrow in Furness,United Kingdom,England,2656241\n" +
            "Barrhead,United Kingdom,Scotland,2656258\n" +
            "Barnstaple,United Kingdom,England,2656281\n" +
            "Barnsley,United Kingdom,England,2656284\n" +
            "Barnet,United Kingdom,England,2656295\n" +
            "Barking,United Kingdom,England,2656333\n" +
            "Banstead,United Kingdom,England,2656379\n" +
            "Bangor,United Kingdom,Northern Ireland,2656396\n" +
            "Bangor,United Kingdom,Wales,2656397\n" +
            "Banbury,United Kingdom,England,2656406\n" +
            "Banbridge,United Kingdom,Northern Ireland,2656407\n" +
            "Ballymena,United Kingdom,Northern Ireland,2656490\n" +
            "Baildon,United Kingdom,England,2656627\n" +
            "Ayr,United Kingdom,Scotland,2656708\n" +
            "Aylesbury,United Kingdom,England,2656719\n" +
            "Atherton,United Kingdom,England,2656847\n" +
            "Ashton-under-Lyne,United Kingdom,England,2656915\n" +
            "Ashton in Makerfield,United Kingdom,England,2656918\n" +
            "Ashford,United Kingdom,England,2656955\n" +
            "Ascot,United Kingdom,England,2656992\n" +
            "Arnold,United Kingdom,England,2657030\n" +
            "Arbroath,United Kingdom,Scotland,2657215\n" +
            "Antrim,United Kingdom,Northern Ireland,2657255\n" +
            "Andover,United Kingdom,England,2657324\n" +
            "Amersham,United Kingdom,England,2657356\n" +
            "Altrincham,United Kingdom,England,2657402\n" +
            "Alton,United Kingdom,England,2657408\n" +
            "Alloa,United Kingdom,Scotland,2657471\n" +
            "Alfreton,United Kingdom,England,2657508\n" +
            "Aldridge,United Kingdom,England,2657528\n" +
            "Aldershot,United Kingdom,England,2657540\n" +
            "Airdrie,United Kingdom,Scotland,2657613\n" +
            "Acton,United Kingdom,England,2657697\n" +
            "Acocks Green,United Kingdom,England,2657703\n" +
            "Accrington,United Kingdom,England,2657770\n" +
            "Abingdon,United Kingdom,England,2657780\n" +
            "Aberystwyth,United Kingdom,Wales,2657782\n" +
            "Abergele,United Kingdom,Wales,2657812\n" +
            "Aberdeen,United Kingdom,Scotland,2657832\n" +
            "Aberdare,United Kingdom,Wales,2657835\n" +
            "Crosby,United Kingdom,England,3209584\n" +
            "Blackwood,United Kingdom,Wales,3345317\n" +
            "Neston,United Kingdom,England,3345432\n" +
            "Camden Town,United Kingdom,England,3345437\n" +
            "Telford,United Kingdom,England,3345439\n" +
            "Craigavon,United Kingdom,Northern Ireland,3345440\n" +
            "Bayswater,United Kingdom,England,6545243\n" +
            "Yateley,United Kingdom,England,6620293\n" +
            "Bowthorpe,United Kingdom,England,6620355\n" +
            "Hedge End,United Kingdom,England,6620360\n" +
            "Erskine,United Kingdom,Scotland,6639623\n" +
            "Hale,United Kingdom,England,6640028\n" +
            "Amersham on the Hill,United Kingdom,England,6690592\n" +
            "Battersea,United Kingdom,England,6690602\n" +
            "South Croydon,United Kingdom,England,6690862\n" +
            "Hornchurch,United Kingdom,England,6690863\n" +
            "Surbiton,United Kingdom,England,6690866\n" +
            "Ewell,United Kingdom,England,6690867\n" +
            "Becontree,United Kingdom,England,6690870\n" +
            "Brixton,United Kingdom,England,6690877\n" +
            "Bethnal Green,United Kingdom,England,6690989\n" +
            "Failsworth,United Kingdom,England,6691219\n" +
            "Radcliffe,United Kingdom,England,6691227\n" +
            "Heywood,United Kingdom,England,6691235\n" +
            "Longsight,United Kingdom,England,6691766\n" +
            "Heavitree,United Kingdom,England,6691884\n" +
            "Ferndown,United Kingdom,England,6691927\n" +
            "Canary Wharf,United Kingdom,England,6692280\n" +
            "Lytham St Annes,United Kingdom,England,6693470\n" +
            "Hadley Wood,United Kingdom,England,6693771\n" +
            "Chapel Allerton,United Kingdom,England,6695247\n" +
            "Blackheath,United Kingdom,England,6947041\n" +
            "Kempston Hardwick,United Kingdom,England,6947168\n" +
            "Mendip,United Kingdom,England,6947756\n" +
            "Lower Earley,United Kingdom,England,7290015";



        String[] arr=str.split("\n");
        for (String a:arr){
            list.add(a);
        }
    }

}