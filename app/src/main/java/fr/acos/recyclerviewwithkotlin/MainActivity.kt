package fr.acos.recyclerviewwithkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.style_dune_ligne.view.*

/**
 * Classe BO permettant de stocker les données à afficher
 */
data class Personne(val id: Long, val nom: String, val prenom: String)

class PersonneAdapter(val personnesAAfficher: Array<Personne>, val listener: (Personne) -> Unit) : RecyclerView.Adapter<PersonneAdapter.ViewHolder>() {

    /**
     * Classe permettant de créer des objets contenant un élément du RecyclerView
     */
    class ViewHolder(val elementDeListe: View) : RecyclerView.ViewHolder(elementDeListe)
    {
        //Cette fonction permet de charger les données dans l'élément du RecyclerView
        fun bind(personne: Personne, listener: (Personne) -> Unit) = with(itemView)
        {
            Log.i("XXX","bind")
            //Remplissage de la partie Nom.
            itemView.tv_nom.text = personne.nom
            //Remplissage de la partie Prénom.
            itemView.tv_prenom.text = personne.prenom
            //Définition de l'action à réaliser lors d'un clic sur l'élément.
            setOnClickListener { listener(personne) }
        }
    }

    /**
     * Créer les éléments ViewHolder du RecyclerView
     */
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): PersonneAdapter.ViewHolder {
        // Création d'une View représentant un élément de liste
        val uneLigneView = LayoutInflater.from(parent.context).inflate(R.layout.style_dune_ligne, parent, false)
        // Transformation de la View en ViewHolder
        return ViewHolder(uneLigneView)
    }

    /**
     * Charge le contenu des données d'un objet dans un élément du RecyclerView
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        Log.i("XXX","onBindViewHolder")
        //On fournit l'objet Personne à charger dans l'élément ainsi que l'action à réaliser lors d'un clic sur l'élément.
        holder.bind(personnesAAfficher[position], listener)
    }

    /**
     * Retourne le nombre d'objets fournit.
     */
    override fun getItemCount() = personnesAAfficher.size
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Bouchon
        val p1 = Personne(1,"Martin","Steve")
        val p2 = Personne(2,"Roussel","John")
        val p3 = Personne(3,"Dupont","Eric")
        val personnes = mutableListOf(p1,p2,p3)

        //Utilisation obligatoire d'un Layout Manager.
        mon_recycler.layoutManager = LinearLayoutManager(this);

        //On lie le RecyclerView à l'adapter.
        mon_recycler.adapter = PersonneAdapter(personnes.toTypedArray())
        {
            //L'utilisation du toast peut être simplifiée avec ANKO.
            Toast.makeText(this,"Elément sélectionné : ${it}",Toast.LENGTH_LONG).show()
        }
    }
}

