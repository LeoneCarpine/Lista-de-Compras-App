
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listadecomprasapp.ListaCategoria
import com.example.listadecomprasapp.ListaItensActivity
import com.example.listadecomprasapp.databinding.BackgroundListaBinding

class ListaAdapter(private val items: List<ListaCategoria>) : RecyclerView.Adapter<ListaAdapter.ListaViewHolder>() {

    // 1. O ViewHolder: Guarda as referências das views de cada item
    inner class ListaViewHolder(val binding: BackgroundListaBinding) : RecyclerView.ViewHolder(binding.root){
        // Dentro de ListaAdapter.kt, na classe ListaViewHolder
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = items[position]

                    // Pega o contexto da view do item
                    val context = itemView.context

                    // Cria a Intent para a nova Activity
                    val intent = Intent(context, ListaItensActivity::class.java).apply {
                        // Adiciona o nome da lista como um "extra"
                        putExtra("LIST_TITLE", clickedItem.titulo)
                    }
                    // Inicia a nova Activity
                    context.startActivity(intent)
                }
            }
        }
    }

    // 2. onCreateViewHolder: Chamado quando o RecyclerView precisa criar um novo item (ViewHolder).
    // Ele infla o XML do item.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaViewHolder {
        val binding = BackgroundListaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListaViewHolder(binding)
    }

    // 3. getItemCount: Retorna o número total de itens na sua lista de dados.
    override fun getItemCount(): Int {
        return items.size
    }

    // 4. onBindViewHolder: Pega os dados de uma posição específica e os conecta com as views do ViewHolder.
    override fun onBindViewHolder(holder: ListaViewHolder, position: Int) {
        val itemAtual = items[position]

        // Aqui conectamos os dados às views
        holder.binding.textViewItemTitle.text = itemAtual.titulo
        holder.binding.imageViewItem.setImageResource(itemAtual.imgId)
    }
}
