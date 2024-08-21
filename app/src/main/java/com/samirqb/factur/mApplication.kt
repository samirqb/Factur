package com.samirqb.factur

import android.app.Application
import com.samirqb.factur.daos.ClienteDao
import com.samirqb.factur.daos.CotizacionDao
import com.samirqb.factur.daos.CuentaDeCobroDao
import com.samirqb.factur.daos.FacturaDao
import com.samirqb.factur.daos.ImagenCorporativaDao
import com.samirqb.factur.daos.ImagenDao
import com.samirqb.factur.daos.ProveedorServicioDao
import com.samirqb.factur.daos.ServicioDao
import com.samirqb.factur.repositories.ClienteRepository
import com.samirqb.factur.repositories.CotizacionRepository
import com.samirqb.factur.repositories.CuentaDeCobroRepository
import com.samirqb.factur.repositories.FacturaRepository
import com.samirqb.factur.repositories.ImagenCorporativaRepository
import com.samirqb.factur.repositories.ImagenRepository
import com.samirqb.factur.repositories.ProveedorServiciosRepository
import com.samirqb.factur.repositories.ServicioRepository

class mApplication: Application() {

    /** Proveedor de servicios ********************************************************************/
    val mProveedorServicioDao : ProveedorServicioDao by lazy { ProveedorServicioDao(context = this) }

    val mProveedorServiciosRepository: ProveedorServiciosRepository by lazy {

        // Initialize your repository here
        val dao = (applicationContext as mApplication).mProveedorServicioDao
        ProveedorServiciosRepository(dao) // Replace with your actual repository implementation
        //ProveedorServiciosRepository((this.applicationContext as mApplication)
            //.mProveedorServicioDao) // Replace with your actual repository implementation
    }


    /** Clientes **********************************************************************************/
    val mClientesDao : ClienteDao by lazy { ClienteDao(context = this) }

    val mClienteRepository: ClienteRepository by lazy {
        val dao = (applicationContext as mApplication).mClientesDao
        ClienteRepository(dao)
    }

    /** Servicios *********************************************************************************/
    val mServicioDao : ServicioDao by lazy { ServicioDao(context = this) }

    val mServicioRepository: ServicioRepository by lazy {
        val dao = (applicationContext as mApplication).mServicioDao
        ServicioRepository(dao)
    }

    /** mCotizacion *******************************************************************************/
    val mCotizacionDao : CotizacionDao by lazy { CotizacionDao(context = this) }


    val mCotizacionRepository: CotizacionRepository by lazy {
        val dao = (applicationContext as mApplication).mCotizacionDao
        CotizacionRepository(dao)
    }

    /** Cuentas de Cobro **************************************************************************/
    val mCuentaDeCobroDao : CuentaDeCobroDao by lazy { CuentaDeCobroDao(context = this) }


    val mCuentaDeCobroRepository: CuentaDeCobroRepository by lazy {
        val dao = (applicationContext as mApplication).mCuentaDeCobroDao
        CuentaDeCobroRepository(dao)
    }

    /** Factura de Cobro **************************************************************************/
    val mFacturaDao : FacturaDao by lazy { FacturaDao(context = this) }


    val mFacturaRepository: FacturaRepository by lazy {
        val dao = (applicationContext as mApplication).mFacturaDao
        FacturaRepository(dao)
    }



    /** Cuentas de Cobro IMAGEN *******************************************************************/

    /** ini-temporal
     *
     * con esta instacia se esta accediendo a la imagen desde el CuentaDeCobroViewModel
     * sin embargo, este acceso se debe correjir y anular, por ahora esta temporal para no afectar la estabilidad de la app */
    val mImagenDao : ImagenDao by lazy { ImagenDao() }

    val mImagenRepository: ImagenRepository by lazy {
        val dao = (applicationContext as mApplication).mImagenDao
        ImagenRepository(dao)
    }

    /** fin-temporal */


    /** esta es la manera correcta para acceder a la imagen corporativa */
    val mImagenCorporativaDao : ImagenCorporativaDao by lazy { ImagenCorporativaDao() }

    val mImagenCorporativaRepository: ImagenCorporativaRepository by lazy {
        val dao = (applicationContext as mApplication).mImagenCorporativaDao
        ImagenCorporativaRepository(dao)
    }



    override fun onCreate() {
        super.onCreate()
    }



    companion object {
        val APPLICATION_KEY = "mApplicationKey" // Unique key for application access
    }

}
