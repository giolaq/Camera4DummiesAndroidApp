package ora.camera4dummies.camera4dummiesandroid;

/**
 * Created by joaobiriba on 18/05/14.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainSparqlQuery extends ListFragment {
    private Context context;
    private static String url = "http://docs.blackberry.com/sampledata.json";

    private static final String VTYPE = "vehicleType";
    private static final String VCOLOR = "vehicleColor";
    private static final String FUEL = "fuel";
    private static final String TREAD = "treadType";

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();

    ListView lv ;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sparqlquerylayout, null);

    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new ProgressTask(getActivity()).execute();

    }
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sparqlquerylayout);
        new ProgressTask(MainSparqlQuery.this).execute();
    }*/

    private class ProgressTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog;

        private FragmentActivity activity;

        // private List<Message> messages;
        public ProgressTask(FragmentActivity activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
        }



        private Context context;

        protected void onPreExecute() {
            this.dialog.setMessage("Progress start");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
           ListAdapter adapter = new SimpleAdapter(context, jsonlist,
                    R.layout.list_item, new String[] { VTYPE, VCOLOR,
                    FUEL, TREAD }, new int[] {
                    R.id.vehicleType, R.id.vehicleColor, R.id.fuel,
                    R.id.treadType });

            setListAdapter(adapter);

            // select single ListView item
            lv = getListView();
        }

        protected Boolean doInBackground(final String... args) {

            JSONParser jParser = new JSONParser();

            // get JSON data from URL
            JSONArray json = jParser.getJSONFromUrl(url);

            for (int i = 0; i < json.length(); i++) {

                try {
                    JSONObject c = json.getJSONObject(i);
                    String vtype = c.getString(VTYPE);

                    String vcolor = c.getString(VCOLOR);
                    String vfuel = c.getString(FUEL);
                    String vtread = c.getString(TREAD);

                    HashMap<String, String> map = new HashMap<String, String>();

                    // Add child node to HashMap key & value
                    map.put(VTYPE, vtype);
                    map.put(VCOLOR, vcolor);
                    map.put(FUEL, vfuel);
                    map.put(TREAD, vtread);
                    jsonlist.add(map);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}


