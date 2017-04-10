import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Model {
	public Model() {
	}

	public void fetchData(BufferedWriter bw, String site) throws Exception {
		URL url = new URL("https://waterdata.usgs.gov/nwis/dv?referred_module=sw&amp;search_site_no="
				+ site
				+ "&amp;search_site_no_match_type=exact&amp;site_tp_cd=OC&amp;site_tp_cd=OC-CO&amp;site_tp_cd=ES&amp;site_tp_cd=LK&amp;site_tp_cd=ST&amp;site_tp_cd=ST-CA&amp;site_tp_cd=ST-DCH&amp;site_tp_cd=ST-TS&amp;group_key=NONE&amp;sitefile_output_format=html_table&amp;column_name=agency_cd&amp;column_name=site_no&amp;column_name=station_nm&amp;range_selection=date_range&amp;begin_date=1838-01-10&amp;end_date=2017-04-09&amp;format=rdb&amp;date_format=YYYY-MM-DD&amp;rdb_compression=value&amp;list_of_search_criteria=search_site_no%2Csite_tp_cd%2Crealtime_parameter_selection");
		URLConnection conn = url.openConnection();

		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		int count = 0;
		String inputLine;
		while ((inputLine = br.readLine()) != null) {
			inputLine = preProcess(inputLine);
			if (inputLine.length() > 0) {
				inputLine = postProcess(inputLine, 9);
				count++;
				bw.write(inputLine);
			}
		}
		br.close();
	}

	public void fetchDescription(BufferedWriter bw, String site) throws Exception {
		URL url = new URL("http://waterdata.usgs.gov/nwis/dv?referred_module=sw&search_site_no=" + site
				+ "&search_site_no_match_type=exact&site_tp_cd=OC&site_tp_cd=OC-CO&site_tp_cd=ES&site_tp_cd=LK&site_tp_cd=ST&site_tp_cd=ST-CA&site_tp_cd=ST-DCH&site_tp_cd=ST-TS&group_key=NONE&format=sitefile_output&sitefile_output_format=rdb&column_name=agency_cd&column_name=site_no&column_name=station_nm&column_name=site_tp_cd&column_name=lat_va&column_name=long_va&column_name=dec_lat_va&column_name=dec_long_va&column_name=coord_meth_cd&column_name=coord_acy_cd&column_name=coord_datum_cd&column_name=dec_coord_datum_cd&column_name=district_cd&column_name=state_cd&column_name=county_cd&column_name=country_cd&column_name=land_net_ds&column_name=map_nm&column_name=map_scale_fc&column_name=alt_va&column_name=alt_meth_cd&column_name=alt_acy_va&column_name=alt_datum_cd&column_name=huc_cd&column_name=basin_cd&column_name=topo_cd&column_name=data_types_cd&column_name=instruments_cd&column_name=construction_dt&column_name=inventory_dt&column_name=drain_area_va&column_name=contrib_drain_area_va&column_name=tz_cd&column_name=local_time_fg&column_name=reliability_cd&column_name=gw_file_cd&column_name=nat_aqfr_cd&column_name=aqfr_cd&column_name=aqfr_type_cd&column_name=well_depth_va&column_name=hole_depth_va&column_name=depth_src_cd&column_name=project_no&column_name=rt_bol&column_name=peak_begin_date&column_name=peak_end_date&column_name=peak_count_nu&column_name=qw_begin_date&column_name=qw_end_date&column_name=qw_count_nu&column_name=gw_begin_date&column_name=gw_end_date&column_name=gw_count_nu&column_name=sv_begin_date&column_name=sv_end_date&column_name=sv_count_nu&range_selection=date_range&begin_date=1916-11-01&end_date=2016-11-09&date_format=YYYY-MM-DD&rdb_compression=value&list_of_search_criteria=search_site_no%2Csite_tp_cd%2Crealtime_parameter_selection");

		URLConnection conn = url.openConnection();

		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		int count = 0;
		String inputLine;
		while ((inputLine = br.readLine()) != null) {
			System.out.println(inputLine);
			inputLine = preProcess(inputLine);
			if (inputLine.length() > 0) {
				inputLine = postProcess(inputLine, 56);
				count++;

				bw.write(inputLine);
			}
		}
		br.close();
	}

	private String postProcess(String inputLine, int column) {
		int count = 0;
		String[] components = inputLine.split("@");
		String[] result = new String[column];
		while (count < column) {
			if ((count < components.length) && (components[count].length() > 0)) {
				String component = components[count].replaceAll(",", "");
				result[count] = component;
			} else {
				result[count] = "null";
			}
			count++;
		}
		return String.join(",", result) + "\n";
	}

	private String preProcess(String inputLine) {
		String[] components = inputLine.split("\t");
		if (!components[0].equals("USGS"))
			return "";
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < inputLine.length(); i++) {
			char c = inputLine.charAt(i);
			if (c == '\t')
				builder.append("@");
			else
				builder.append(c);
		}
		return builder.toString();
	}
}