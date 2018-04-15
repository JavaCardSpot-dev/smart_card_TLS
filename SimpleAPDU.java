package simpleapdu;
import applets.eapengine;
import cardTools.CardManager;
import cardTools.RunConfig;
import cardTools.Util;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

public class SimpleAPDU {
    private static String APPLET_AID = "73696D706C656170706C6574";
    private static byte APPLET_AID_BYTE[] = Util.hexStringToByteArray(APPLET_AID);

	private static final String STR_APDU_USER_PIN_VERIFY = "A02000000830303030FFFFFFFF";// 
	private static final String STR_APDU_OPER_PIN_VERIFY = "A0200001083030303030303030";
	  
	private static final String STR_APDU_CHANGE_OPER_PIN = "A02400011030303030303030300101000901030100";
	private static final String STR_APDU_CHANGE_USER_PIN =  "A02400001030303030FFFFFFFF0103010001010009";
	
	private static final String STR_APDU_USER_PIN1_VERIFY = "A0200000080103010001010009";// 
	private static final String STR_APDU_OPER_PIN1_VERIFY = "A0200001080101000901030100";
	 
	private static final String STR_EAP_START =  "A08000000601A500060D20";//This is giving default response
 
	/**
     * Main entry point.
     *
     * @param args
    */
    public static void main(String[] args) {
        try {
            SimpleAPDU main = new SimpleAPDU();
			main.sign_data_sim();//call this to run applet on simulated card
        }catch (Exception ex) {
			System.out.println("Exception : " + ex);
        }
    }

    public void sign_data_sim() throws Exception {
		final CardManager cardMngr = new CardManager(true, APPLET_AID_BYTE);          
		final RunConfig runCfg = RunConfig.getDefaultConfig();
            
        //B) If running in the simulator 
		runCfg.setAppletToSimulate(eapengine.class); // main class of applet to simulate
		runCfg.setTestCardType(RunConfig.CARD_TYPE.JCARDSIMLOCAL); // Use local simulator
		//physical
		//runCfg.setTestCardType(RunConfig.CARD_TYPE.PHYSICAL);
     
        System.out.print("Connecting to card...");
        if (!cardMngr.Connect(runCfg)) {
            System.out.println(" Failed.");
        }
        System.out.println(" Done.");
        long countTIME=0;
               
             
        final ResponseAPDU response_UserPIN = cardMngr.transmit(new CommandAPDU(Util.hexStringToByteArray(STR_APDU_USER_PIN_VERIFY)));
        System.out.println(response_UserPIN);
        
        final ResponseAPDU response_OPER_PIN = cardMngr.transmit(new CommandAPDU(Util.hexStringToByteArray(STR_APDU_OPER_PIN_VERIFY)));
        System.out.println(response_OPER_PIN);
        
        final ResponseAPDU response_CH_USER = cardMngr.transmit(new CommandAPDU(Util.hexStringToByteArray(STR_APDU_CHANGE_USER_PIN)));
        System.out.println(response_CH_USER);
        
        final ResponseAPDU response_CH_OPER = cardMngr.transmit(new CommandAPDU(Util.hexStringToByteArray(STR_APDU_CHANGE_OPER_PIN)));
        System.out.println(response_CH_OPER);
         
		//TO TEST THE CHANGED PIN  
        final ResponseAPDU response_UserPIN1 = cardMngr.transmit(new CommandAPDU(Util.hexStringToByteArray(STR_APDU_USER_PIN1_VERIFY)));
        System.out.println(response_UserPIN1);
        
        final ResponseAPDU response_OPER_PIN1 = cardMngr.transmit(new CommandAPDU(Util.hexStringToByteArray(STR_APDU_OPER_PIN1_VERIFY)));
        System.out.println(response_OPER_PIN1);
              
        
		// final ResponseAPDU response_EAP_START = cardMngr.transmit(new CommandAPDU(Util.hexStringToByteArray(STR_EAP_START)));
		// System.out.println(response_EAP_START);
        cardMngr.Disconnect(true);
     } 
    
   }
