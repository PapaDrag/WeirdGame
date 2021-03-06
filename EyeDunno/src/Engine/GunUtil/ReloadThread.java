package Engine.GunUtil;

public class ReloadThread extends Thread {

    public int seconds;
    private Gun gun;

    public ReloadThread(int seconds, Gun gun){
        this.seconds = seconds;
        this.gun = gun;

    }

    @Override
    public void run(){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (gun.ammoInReserve >= gun.magazineSize){
            int initAmmoMissingInMag = gun.magazineSize - gun.ammoInMag;
            gun.ammoInMag = gun.magazineSize;
            gun.ammoInReserve -= initAmmoMissingInMag;

        }
        else{
            gun.ammoInMag = gun.ammoInReserve;
            gun.ammoInReserve = 0;
        }
        gun.isReloading = false;
    }
}
