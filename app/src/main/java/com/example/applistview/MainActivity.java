package com.example.applistview;

// Importações de classes necessárias para o código funcionar
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView; // Importado para "ouvir" cliques nos itens da lista
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast; // Importado para mostrar pequenas mensagens (pop-ups)

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList; // Importa a classe para criar listas dinâmicas

// Declaração da classe principal da sua tela (Activity)
public class MainActivity extends AppCompatActivity {

    // 1. Declaração das variáveis (componentes de UI e dados)

    // Variável para o campo de texto
    private TextInputEditText edtNome;
    // Variável para o botão
    private Button btnInserir;
    // Variável para a lista
    private ListView listView;
    // Variável para a lista (ArrayList) que guardará os nomes (os dados)
    private ArrayList<String> nomes;

    // Declaração da variável do Adapter aqui para que ela seja acessível
    // em todos os métodos da classe (no onCreate, no setOnItemLongClickListener, etc.)
    private ArrayAdapter<String> adapter;


    // Método principal, executado quando a tela é criada
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Linha padrão, chama o método da classe pai

        // Código padrão para habilitar o modo "Edge-to-Edge" (tela cheia)
        EdgeToEdge.enable(this);

        // Conecta este arquivo Java com o arquivo XML (R.layout.activity_main)
        setContentView(R.layout.activity_main);

        // Código padrão que lida com as barras do sistema (status bar) no modo Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 2. "Linkar" as variáveis Java com os componentes do XML

        // Procura a View no XML pelo ID "edtNome" e a atribui à variável "edtNome"
        edtNome = findViewById(R.id.edtNome);
        // Procura a View no XML pelo ID "btnInserir" e a atribui à variável "btnInserir"
        btnInserir = findViewById(R.id.btnInserir);
        // Procura a View no XML pelo ID "listView" e a atribui à variável "listView"
        listView = findViewById(R.id.listView);

        // 3. Preparar a lista e o "Adapter"

        // Inicializa a lista de nomes como uma nova lista vazia
        nomes = new ArrayList<>();

        // Cria um "ArrayAdapter". Ele é a "ponte" que conecta seus dados (o ArrayList "nomes")
        // com a interface (o "listView").
        // Ele usará um layout padrão do Android (simple_list_item_1) para exibir cada nome.
        // **Mudança**: Atribui à variável "adapter" que declaramos lá em cima
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nomes);

        // Define o "adapter" recém-criado como o adaptador oficial do seu "listView"
        listView.setAdapter(adapter);

        // 4. Configurar o "ouvinte de clique" do botão INSERIR

        // Define o que acontece quando o "btnInserir" é clicado
        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Este método é executado no clique

                // Pega o texto do campo "edtNome", converte para String
                // e ".trim()" remove espaços em branco inúteis do início e do fim
                String nome = edtNome.getText().toString().trim();

                // Verifica se a string "nome" NÃO está vazia
                if (!nome.isEmpty()) {
                    // Se não estiver vazia:

                    // 1. Adiciona o nome à sua lista de dados (o ArrayList)
                    nomes.add(nome);

                    // 2. AVISA o "adapter" que os dados mudaram.
                    // Isso faz o "listView" se atualizar e mostrar o novo nome.
                    adapter.notifyDataSetChanged();
                }

                // Limpa o campo de texto, deixando-o pronto para o próximo nome
                edtNome.setText("");
            }
        });

        // 5. Configurar o "ouvinte de clique" para ITENS DA LISTA (Clique Curto)

        // Define o que acontece quando um item da "listView" recebe um clique curto
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 'position' é o número (índice) do item que foi clicado (começa em 0)

                // Pega o nome da lista de dados usando a posição que recebemos
                String nomeClicado = nomes.get(position);

                // Cria e exibe um "Toast" (uma mensagem rápida) mostrando o nome
                Toast.makeText(MainActivity.this, "Clique curto: " + nomeClicado, Toast.LENGTH_SHORT).show();
            }
        });

        // 6. Configurar o "ouvinte de clique" para ITENS DA LISTA (Clique Longo)

        // Define o que acontece quando um item da "listView" é pressionado por mais tempo
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 'position' é o índice do item que recebeu o clique longo

                // Pega o nome que será removido (apenas para mostrar no Toast)
                String nomeRemovido = nomes.get(position);

                // 1. Remove o item da lista de dados (ArrayList) usando sua posição
                nomes.remove(position);

                // 2. AVISA o "adapter" que os dados mudaram (um item foi removido)
                // Isso faz o "listView" se atualizar e remover o nome da tela.
                adapter.notifyDataSetChanged();

                // Mostra um Toast avisando qual nome foi removido
                Toast.makeText(MainActivity.this, "Nome removido: " + nomeRemovido, Toast.LENGTH_SHORT).show();

                // 'return true;' informa ao Android que o clique longo foi "consumido" (tratado).
                // Isso evita que o clique curto (onItemClick) seja disparado também.
                return true;
            }
        });

    }
}