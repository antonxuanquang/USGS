import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

public class Control implements ActionListener {
	Model model;
	View view;
	private String siteList;

	public Control(Model model, View view) {
		this.model = model;
		this.view = view;

		view.chooseFile.addActionListener(this);
		view.run.addActionListener(this);
	}

	protected void chooseSiteFile() {
		JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));

		int returnVal = fc.showOpenDialog(view.chooseFile);

		if (returnVal == 0) {
			File file = fc.getSelectedFile();

			siteList = file.getName();
			view.report.setText("Get site list from " + file.getName() + "." + "\n");
			view.run.setEnabled(true);
		} else {
			view.report.append("Open command cancelled by user.\n");
		}
	}

	public void fetchDescription() {
		try {
			File file = new File("site_description.csv");
			file.createNewFile();

			File citeFile = new File(siteList);
			BufferedReader br = new BufferedReader(new FileReader(citeFile));

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("agency_cd,site_no,station_nm,site_tp_cd,lat_va,"
					+ "long_va,dec_lat_va,dec_long_va,coord_meth_cd,"
					+ "coord_acy_cd,coord_datum_cd,dec_coord_datum_cd,"
					+ "district_cd,state_cd,county_cd,country_cd,"
					+ "land_net_ds,map_nm,map_scale_fc,alt_va,"
					+ "alt_meth_cd,alt_acy_va,alt_datum_cd,huc_cd,"
					+ "basin_cd,topo_cd,data_types_cd,instruments_cd,"
					+ "construction_dt,inventory_dt,drain_area_va,"
					+ "contrib_drain_area_va,tz_cd,local_time_fg,"
					+ "reliability_cd,gw_file_cd,nat_aqfr_cd,aqfr_cd,"
					+ "aqfr_type_cd,well_depth_va,hole_depth_va,"
					+ "depth_src_cd,project_no,rt_bol,peak_begin_date,"
					+ "peak_end_date,peak_count_nu,qw_begin_date,"
					+ "qw_end_date,qw_count_nu,gw_begin_date,gw_end_date,"
					+ "gw_count_nu,sv_begin_date,sv_end_date,sv_count_nu\n");

			String inputLine;
			view.report.append("Start Fetching Site Descriptions\n");
			while ((inputLine = br.readLine()) != null) {
				try {
					model.fetchDescription(bw, inputLine, 
							view.firstPartDescription.getText(), view.secondPartDescription.getText());
					view.report.append("Done with site " + inputLine + "\n");
					view.repaint();
				} catch (Exception e) {
					view.report.append("Error! Can't pull description from site " + inputLine + "\n");
				}
			}
			view.report.append("Finish Fetchich Site Descriptions\n");

			bw.close();
			br.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fetchData() {
		try {
			File file = new File("site_data.csv");
			file.createNewFile();

			File citeFile = new File(siteList);
			BufferedReader br = new BufferedReader(new FileReader(citeFile));

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("agency_cd,site_no,datetime,00060_00003,00060_00003_cd,80154_00003,80154_00003_cd,80155_00003,80155_00003_cd\n");

			String inputLine;
			view.report.append("Start Fetching Site Data\n");
			while ((inputLine = br.readLine()) != null) {
				try {
					model.fetchData(bw, inputLine,
							view.firstPartData.getText(), view.secondPartData.getText());
					view.report.append("Done with site " + inputLine + "\n");
					view.repaint();
				} catch (Exception e) {
					view.report.append("Error! Can't pull data from site " + inputLine + "\n");
				}
			}
			view.report.append("Finish Fetching Site Data\n");

			bw.close();
			br.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Model model = new Model();
		View view = new View();
		Control control = new Control(model, view);
//		view.chooseFile.doClick();
//		view.run.doClick();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.chooseFile) {
			chooseSiteFile();
		} else if (e.getSource() == view.run) {
			fetchDescription();
			fetchData();
		}
	}
}