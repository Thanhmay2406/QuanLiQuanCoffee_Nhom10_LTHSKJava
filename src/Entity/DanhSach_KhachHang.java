package Entity;

import java.time.LocalDate;
import java.util.ArrayList;

import DAO.KhachHang_DAO;

public class DanhSach_KhachHang {
	private ArrayList<KhachHang> ds;
	private KhachHang_DAO daoKH;

	private String[] ArrMa = { "KH001", "KH002", "KH003", "KH004", "KH005", "KH006", "KH007", "KH008", "KH009", "KH010",
			"KH011", "KH012", "KH013", "KH014", "KH015", "KH016", "KH017", "KH018", "KH019", "KH020", "KH021", "KH022",
			"KH023", "KH024", "KH025", "KH026", "KH027", "KH028", "KH029", "KH030", "KH031", "KH032", "KH033", "KH034",
			"KH035", "KH036", "KH037", "KH038", "KH039", "KH040", "KH041", "KH042", "KH043", "KH044", "KH045", "KH046",
			"KH047", "KH048", "KH049", "KH050", "KH051", "KH052", "KH053", "KH054", "KH055", "KH056", "KH057", "KH058",
			"KH059", "KH060", "KH061", "KH062", "KH063", "KH064", "KH065", "KH066", "KH067", "KH068", "KH069", "KH070",
			"KH071", "KH072", "KH073", "KH074", "KH075", "KH076", "KH077", "KH078", "KH079", "KH080", "KH081", "KH082",
			"KH083", "KH084", "KH085", "KH086", "KH087", "KH088", "KH089", "KH090", "KH091", "KH092", "KH093", "KH094",
			"KH095", "KH096", "KH097", "KH098", "KH099", "KH100", "KH101", "KH102", "KH103", "KH104", "KH105", "KH106",
			"KH107", "KH108", "KH109", "KH110", "KH111", "KH112", "KH113", "KH114", "KH115", "KH116", "KH117", "KH118",
			"KH119", "KH120", "KH121", "KH122", "KH123", "KH124", "KH125", "KH126", "KH127", "KH128", "KH129", "KH130",
			"KH131", "KH132", "KH133", "KH134", "KH135", "KH136", "KH137", "KH138", "KH139", "KH140", "KH141", "KH142",
			"KH143", "KH144", "KH145", "KH146", "KH147", "KH148", "KH149", "KH150", "KH151", "KH152", "KH153", "KH154",
			"KH155", "KH156", "KH157", "KH158", "KH159", "KH160", "KH161", "KH162", "KH163", "KH164", "KH165", "KH166",
			"KH167", "KH168", "KH169", "KH170", "KH171", "KH172", "KH173", "KH174", "KH175", "KH176", "KH177", "KH178",
			"KH179", "KH180", "KH181", "KH182", "KH183", "KH184", "KH185", "KH186", "KH187", "KH188", "KH189", "KH190",
			"KH191", "KH192", "KH193", "KH194", "KH195", "KH196", "KH197", "KH198", "KH199", "KH200", "KH201", "KH202",
			"KH203", "KH204", "KH205", "KH206", "KH207", "KH208", "KH209", "KH210", "KH211", "KH212", "KH213", "KH214",
			"KH215", "KH216", "KH217", "KH218", "KH219", "KH220", "KH221", "KH222", "KH223", "KH224", "KH225", "KH226",
			"KH227", "KH228", "KH229", "KH230", "KH231", "KH232", "KH233", "KH234", "KH235", "KH236", "KH237", "KH238",
			"KH239", "KH240", "KH241", "KH242", "KH243", "KH244", "KH245", "KH246", "KH247", "KH248", "KH249", "KH250",
			"KH251", "KH252", "KH253", "KH254", "KH255", "KH256", "KH257", "KH258", "KH259", "KH260", "KH261", "KH262",
			"KH263", "KH264", "KH265", "KH266", "KH267", "KH268", "KH269", "KH270", "KH271", "KH272", "KH273", "KH274",
			"KH275", "KH276", "KH277", "KH278", "KH279", "KH280", "KH281", "KH282", "KH283", "KH284", "KH285", "KH286",
			"KH287", "KH288", "KH289", "KH290", "KH291", "KH292", "KH293", "KH294", "KH295", "KH296", "KH297", "KH298",
			"KH299", "KH300", "KH301", "KH302", "KH303", "KH304", "KH305", "KH306", "KH307", "KH308", "KH309", "KH310",
			"KH311", "KH312", "KH313", "KH314", "KH315", "KH316", "KH317", "KH318", "KH319", "KH320", "KH321", "KH322",
			"KH323", "KH324", "KH325", "KH326", "KH327", "KH328", "KH329", "KH330", "KH331", "KH332", "KH333", "KH334",
			"KH335", "KH336", "KH337", "KH338", "KH339", "KH340", "KH341", "KH342", "KH343", "KH344", "KH345", "KH346",
			"KH347", "KH348", "KH349", "KH350", "KH351", "KH352", "KH353", "KH354", "KH355", "KH356", "KH357", "KH358",
			"KH359", "KH360", "KH361", "KH362", "KH363", "KH364", "KH365", "KH366", "KH367", "KH368", "KH369", "KH370",
			"KH371", "KH372", "KH373", "KH374", "KH375", "KH376", "KH377", "KH378", "KH379", "KH380", "KH381", "KH382",
			"KH383", "KH384", "KH385", "KH386", "KH387", "KH388", "KH389", "KH390", "KH391", "KH392", "KH393", "KH394",
			"KH395", "KH396", "KH397", "KH398", "KH399", "KH400", "KH401", "KH402", "KH403", "KH404", "KH405", "KH406",
			"KH407", "KH408", "KH409", "KH410", "KH411", "KH412", "KH413", "KH414", "KH415", "KH416", "KH417", "KH418",
			"KH419", "KH420", "KH421", "KH422", "KH423", "KH424", "KH425", "KH426", "KH427", "KH428", "KH429", "KH430",
			"KH431", "KH432", "KH433", "KH434", "KH435", "KH436", "KH437", "KH438", "KH439", "KH440", "KH441", "KH442",
			"KH443", "KH444", "KH445", "KH446", "KH447", "KH448", "KH449", "KH450", "KH451", "KH452", "KH453", "KH454",
			"KH455", "KH456", "KH457", "KH458", "KH459", "KH460", "KH461", "KH462", "KH463", "KH464", "KH465", "KH466",
			"KH467", "KH468", "KH469", "KH470", "KH471", "KH472", "KH473", "KH474", "KH475", "KH476", "KH477", "KH478",
			"KH479", "KH480", "KH481", "KH482", "KH483", "KH484", "KH485", "KH486", "KH487", "KH488", "KH489", "KH490",
			"KH491", "KH492", "KH493", "KH494", "KH495", "KH496", "KH497", "KH498", "KH499", "KH500", "KH501", "KH502",
			"KH503", "KH504", "KH505", "KH506", "KH507", "KH508", "KH509", "KH510", "KH511", "KH512", "KH513", "KH514",
			"KH515", "KH516", "KH517", "KH518", "KH519", "KH520", "KH521", "KH522", "KH523", "KH524", "KH525", "KH526",
			"KH527", "KH528", "KH529", "KH530", "KH531", "KH532", "KH533", "KH534", "KH535", "KH536", "KH537", "KH538",
			"KH539", "KH540", "KH541", "KH542", "KH543", "KH544", "KH545", "KH546", "KH547", "KH548", "KH549", "KH550",
			"KH551", "KH552", "KH553", "KH554", "KH555", "KH556", "KH557", "KH558", "KH559", "KH560", "KH561", "KH562",
			"KH563", "KH564", "KH565", "KH566", "KH567", "KH568", "KH569", "KH570", "KH571", "KH572", "KH573", "KH574",
			"KH575", "KH576", "KH577", "KH578", "KH579", "KH580", "KH581", "KH582", "KH583", "KH584", "KH585", "KH586",
			"KH587", "KH588", "KH589", "KH590", "KH591", "KH592", "KH593", "KH594", "KH595", "KH596", "KH597", "KH598",
			"KH599", "KH600", "KH601", "KH602", "KH603", "KH604", "KH605", "KH606", "KH607", "KH608", "KH609", "KH610",
			"KH611", "KH612", "KH613", "KH614", "KH615", "KH616", "KH617", "KH618", "KH619", "KH620", "KH621", "KH622",
			"KH623", "KH624", "KH625", "KH626", "KH627", "KH628", "KH629", "KH630", "KH631", "KH632", "KH633", "KH634",
			"KH635", "KH636", "KH637", "KH638", "KH639", "KH640", "KH641", "KH642", "KH643", "KH644", "KH645", "KH646",
			"KH647", "KH648", "KH649", "KH650", "KH651", "KH652", "KH653", "KH654", "KH655", "KH656", "KH657", "KH658",
			"KH659", "KH660", "KH661", "KH662", "KH663", "KH664", "KH665", "KH666", "KH667", "KH668", "KH669", "KH670",
			"KH671", "KH672", "KH673", "KH674", "KH675", "KH676", "KH677", "KH678", "KH679", "KH680", "KH681", "KH682",
			"KH683", "KH684", "KH685", "KH686", "KH687", "KH688", "KH689", "KH690", "KH691", "KH692", "KH693", "KH694",
			"KH695", "KH696", "KH697", "KH698", "KH699", "KH700", "KH701", "KH702", "KH703", "KH704", "KH705", "KH706",
			"KH707", "KH708", "KH709", "KH710", "KH711", "KH712", "KH713", "KH714", "KH715", "KH716", "KH717", "KH718",
			"KH719", "KH720", "KH721", "KH722", "KH723", "KH724", "KH725", "KH726", "KH727", "KH728", "KH729", "KH730",
			"KH731", "KH732", "KH733", "KH734", "KH735", "KH736", "KH737", "KH738", "KH739", "KH740", "KH741", "KH742",
			"KH743", "KH744", "KH745", "KH746", "KH747", "KH748", "KH749", "KH750", "KH751", "KH752", "KH753", "KH754",
			"KH755", "KH756", "KH757", "KH758", "KH759", "KH760", "KH761", "KH762", "KH763", "KH764", "KH765", "KH766",
			"KH767", "KH768", "KH769", "KH770", "KH771", "KH772", "KH773", "KH774", "KH775", "KH776", "KH777", "KH778",
			"KH779", "KH780", "KH781", "KH782", "KH783", "KH784", "KH785", "KH786", "KH787", "KH788", "KH789", "KH790",
			"KH791", "KH792", "KH793", "KH794", "KH795", "KH796", "KH797", "KH798", "KH799", "KH800", "KH801", "KH802",
			"KH803", "KH804", "KH805", "KH806", "KH807", "KH808", "KH809", "KH810", "KH811", "KH812", "KH813", "KH814",
			"KH815", "KH816", "KH817", "KH818", "KH819", "KH820", "KH821", "KH822", "KH823", "KH824", "KH825", "KH826",
			"KH827", "KH828", "KH829", "KH830", "KH831", "KH832", "KH833", "KH834", "KH835", "KH836", "KH837", "KH838",
			"KH839", "KH840", "KH841", "KH842", "KH843", "KH844", "KH845", "KH846", "KH847", "KH848", "KH849", "KH850",
			"KH851", "KH852", "KH853", "KH854", "KH855", "KH856", "KH857", "KH858", "KH859", "KH860", "KH861", "KH862",
			"KH863", "KH864", "KH865", "KH866", "KH867", "KH868", "KH869", "KH870", "KH871", "KH872", "KH873", "KH874",
			"KH875", "KH876", "KH877", "KH878", "KH879", "KH880", "KH881", "KH882", "KH883", "KH884", "KH885", "KH886",
			"KH887", "KH888", "KH889", "KH890", "KH891", "KH892", "KH893", "KH894", "KH895", "KH896", "KH897", "KH898",
			"KH899", "KH900", "KH901", "KH902", "KH903", "KH904", "KH905", "KH906", "KH907", "KH908", "KH909", "KH910",
			"KH911", "KH912", "KH913", "KH914", "KH915", "KH916", "KH917", "KH918", "KH919", "KH920", "KH921", "KH922",
			"KH923", "KH924", "KH925", "KH926", "KH927", "KH928", "KH929", "KH930", "KH931", "KH932", "KH933", "KH934",
			"KH935", "KH936", "KH937", "KH938", "KH939", "KH940", "KH941", "KH942", "KH943", "KH944", "KH945", "KH946",
			"KH947", "KH948", "KH949", "KH950", "KH951", "KH952", "KH953", "KH954", "KH955", "KH956", "KH957", "KH958",
			"KH959", "KH960", "KH961", "KH962", "KH963", "KH964", "KH965", "KH966", "KH967", "KH968", "KH969", "KH970",
			"KH971", "KH972", "KH973", "KH974", "KH975", "KH976", "KH977", "KH978", "KH979", "KH980", "KH981", "KH982",
			"KH983", "KH984", "KH985", "KH986", "KH987", "KH988", "KH989", "KH990", "KH991", "KH992", "KH993", "KH994",
			"KH995", "KH996", "KH997", "KH998", "KH999" };

	public DanhSach_KhachHang() {
		ds = new ArrayList<KhachHang>();
		daoKH = new KhachHang_DAO();
		ds = daoKH.layTatCa();
	}

	
	
	public ArrayList<KhachHang> getDs() {
		return ds;
	}


	public KhachHang timKiem_MaKH(String s) {
		for (KhachHang it : ds) {
			if (it.getMaKhachHang().equals(s)) {
				return it;
			}
		}
		return null;
	}
	
	public int timKiem_STD(String s) {
		for (int i=0 ; i<ds.size() ; i++) {
			if (ds.get(i).getSoDienThoai().equals(s)) {
				return i;
			}
		}
		return -1;
	}
	
	
	public String taoMaKH() {
		for (int i=0 ;i<999 ; i++) {
			if (timKiem_MaKH(ArrMa[i]) == null) {
				return ArrMa[i];
			}
		}
		return "";
	}

	public void themKhachHang(String hoTen, String std, String email, String diaChi) {
		String maKH = taoMaKH();
		ds.add(new KhachHang(maKH, hoTen, std, email, diaChi, 0.0, LocalDate.now()));
		
//		String maKhachHang, String hoTen, String soDienThoai, String email, String diaChi,
//		double diemTichLuy, LocalDate ngayDangKy
	}
	
	public boolean xoaKhachHang(String maKH) {
		if (timKiem_MaKH(maKH) == null) return false;
		ds.remove(timKiem_MaKH(maKH));
		return true;
	}
	
	public void capNhatKH(KhachHang X) {
		for (int i=0 ; i<ds.size(); i++) {
			KhachHang it = ds.get(i);
			if (it.getMaKhachHang().equals(X.getMaKhachHang())) {
				ds.set(i, X);
				break;
			}
		}
	}
	
	public boolean luuTatCa() {
		if (daoKH.luuTatCa(ds)) return true;
		return false;
	}
}
