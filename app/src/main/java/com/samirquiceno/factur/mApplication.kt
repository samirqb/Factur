package com.samirquiceno.factur

import android.app.Application
import com.samirquiceno.factur.daos.ClienteDao
import com.samirquiceno.factur.daos.CuentaDeCobroDao
import com.samirquiceno.factur.daos.ImagenDao
import com.samirquiceno.factur.daos.ProveedorServicioDao
import com.samirquiceno.factur.daos.ServicioDao
import com.samirquiceno.factur.repositories.ClienteRepository
import com.samirquiceno.factur.repositories.CuentaDeCobroRepository
import com.samirquiceno.factur.repositories.ImagenRepository
import com.samirquiceno.factur.repositories.ProveedorServiciosRepository
import com.samirquiceno.factur.repositories.ServicioRepository

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

    /** Cuentas de Cobro **************************************************************************/
    val mCuentaDeCobroDao : CuentaDeCobroDao by lazy { CuentaDeCobroDao(context = this) }


    val mCuentaDeCobroRepository: CuentaDeCobroRepository by lazy {
        val dao = (applicationContext as mApplication).mCuentaDeCobroDao
        CuentaDeCobroRepository(dao)
    }

    /** Cuentas de Cobro IMAGEN *******************************************************************/
    val mImagenDao : ImagenDao by lazy { ImagenDao() }


    val mImagenRepository: ImagenRepository by lazy {
        val dao = (applicationContext as mApplication).mImagenDao
        ImagenRepository(dao)
    }



    override fun onCreate() {
        super.onCreate()
    }



    companion object {
        val APPLICATION_KEY = "mApplicationKey" // Unique key for application access
    }

}
