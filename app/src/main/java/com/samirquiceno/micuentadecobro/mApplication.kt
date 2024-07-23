package com.samirquiceno.micuentadecobro

import android.app.Application
import com.samirquiceno.micuentadecobro.daos.ClienteDao
import com.samirquiceno.micuentadecobro.daos.CuentaDeCobroDao
import com.samirquiceno.micuentadecobro.daos.ImagenDao
import com.samirquiceno.micuentadecobro.daos.ProveedorServicioDao
import com.samirquiceno.micuentadecobro.daos.ServicioDao
import com.samirquiceno.micuentadecobro.repositories.ClienteRepository
import com.samirquiceno.micuentadecobro.repositories.CuentaDeCobroRepository
import com.samirquiceno.micuentadecobro.repositories.ImagenRepository
import com.samirquiceno.micuentadecobro.repositories.ProveedorServiciosRepository
import com.samirquiceno.micuentadecobro.repositories.ServicioRepository

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
