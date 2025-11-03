<div align="center"><h1>🎨 Doodlz - Aplicativo de Desenho Android 🖌️</h1><p><strong>Um aplicativo de desenho nativo para Android, escrito em Java, que funciona como uma tela de pintura digital com suporte a multi-toque, paleta de cores, e salvamento de imagens.</strong></p><p><img alt="Status do Projeto" src="https://img.shields.io/badge/Status-Completo_(Demo)-brightgreen?style=for-the-badge"><img alt="Linguagem" src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"><img alt="Plataforma" src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white"><img alt="UI" src="https://img.shields.io/badge/UI-Custom%20View-blueviolet?style=for-the-badge"></p></div>

----------------------------------------------------------------------------------------------------------------------------
📖 Sobre o Projeto

  Doodlz é uma aplicação de desenho para Android que transforma a tela do dispositivo numa tela de pintura. O coração do projeto é uma View personalizada (DoodleView) que captura e renderiza os movimentos dos dedos em tempo real.

  O aplicativo não se limita a um único traço; ele foi desenhado para gerir múltiplos toques (multi-touch) simultaneamente, permitindo que o utilizador desenhe com vários dedos de uma vez. Além disso, o app inclui um menu de ferramentas completo para alterar cores, espessura da linha, salvar, imprimir e apagar o desenho.
  
  Uma das funcionalidades mais interessantes é o uso do acelerómetro do dispositivo: o utilizador pode simplesmente agitar o telemóvel para limpar a tela.

--------------------------------------------------------------------------------------------------------------------------- 
✨ Funcionalidades Principais

1. ✍️Desenho Multi-Touch: Capacidade de desenhar com vários dedos ao mesmo tempo. Cada toque é rastreado com um Path individual.

2. 🎨 Seletor de Cores: Um DialogFragment personalizado (ColorDialogFragment) com um RecyclerView que exibe uma paleta de cores para o pincel.

3. 〰️ Seletor de Espessura: Um DialogFragment (LineWidthDialogFragment) que usa um SeekBar para permitir ao utilizador ajustar a espessura da linha.

4. Gestão de Ações:

   💾 Salvar: Salva o desenho atual na galeria de fotos do dispositivo usando MediaStore.

   🖨️ Imprimir: Envia o desenho para o serviço de impressão do Android.

   🗑️ Apagar: Um DialogFragment (EraseImageDialogFragment) pede confirmação antes de limpar a tela.

5. Uso de Sensor (Hardware):

    📳 Apagar ao Agitar: Utiliza o Acelerómetro (Sensor.TYPE_ACCELEROMETER) para detetar um gesto de "agitar" (shake) e apagar o desenho automaticamente.

----------------------------------------------------------------------------------------------------------------------------
🛠️ Pilha de Tecnologias (Tech Stack)

Linguagem Java: Lógica principal do aplicativo.

Framework (Android SDK): Framework nativo para desenvolvimento Android.

Arquitetura (Fragmentos + Atividade Única): MainActivity hospeda DoodleFragment.

Interface (UI): Custom View (DoodleView). O coração do app, uma View personalizada para desenhar.

Gráficos (Bitmap, Canvas, Paint, Path): APIs de desenho 2D do Android.

Sensores (SensorManager): Usado para aceder ao Acelerómetro.

Permissões (AndroidManifest.xml): Solicita WRITE_EXTERNAL_STORAGE para salvar imagens.

Build (Gradle - Kotlin DSL): Sistema de build e gestão de dependências.


----------------------------------------------------------------------------------------------------------------------------
🔑 Destaques da Implementação

1. DoodleView.java (A Tela de Pintura Multi-Toque):

  A classe DoodleView é uma View personalizada que gere toda a lógica de desenho.
  
  1. Ela usa um Bitmap como tela de fundo (bitmap) e um Canvas (bitmapCanvas) associado a ele.
    
  2. Para suportar multi-toque, ela utiliza dois HashMap:

     pathMap: Armazena o Path (o traço) para cada dedo (identificado pelo seu pointerId).

     previousPointMap: Armazena o ponto anterior de cada dedo para criar linhas suaves.
  
  3. O método onTouchEvent é onde a magia acontece, processando ACTION_DOWN, ACTION_POINTER_DOWN, ACTION_MOVE, ACTION_UP, e ACTION_POINTER_UP para desenhar os traços no bitmapCanvas.

1. SensorEventListenerHelper.java: Apagar ao Agitar

   Esta classe encapsula a lógica do acelerómetro. Ela ouve as mudanças no sensor e calcula a aceleração atual. Se a aceleração exceder um limite (SHAKE_THRESHOLD), ela invoca o método eraseImage() no DoodleFragment.

----------------------------------------------------------------------------------------------------------------------------
📂 Estrutura do Repositório

doodlz/

│

├── app/

│   ├── build.gradle.kts          # Configurações do módulo 'app'

│   └── src/

│       ├── main/

│       │   ├── java/com/example/doodlz/

│       │   │   ├── MainActivity.java     # Atividade principal (Host)

│       │   │   ├── DoodleFragment.java     # Fragmento principal (Controlador)

│       │   │   ├── DoodleView.java       # <-- A LÓGICA DE DESENHO

│       │   │   ├── SensorEventListenerHelper.java # <-- LÓGICA DO ACELERÓMETRO

│       │   │   ├── ColorDialogFragment.java  # Dialog de Cor

│       │   │   ├── LineWidthDialogFragment.java # Dialog de Espessura

│       │   │   └── EraseImageDialogFragment.java # Dialog de Apagar

│       │   │

│       │   ├── res/

│       │   │   ├── layout/               # Layouts XML

│       │   │   │   ├── activity_main.xml

│       │   │   │   ├── fragment_doodle.xml

│       │   │   │   ├── fragment_color.xml

│       │   │   │   └── fragment_line_width.xml

│       │   │   ├── drawable/             # Ícones e vetores

│       │   │   └── values/               # Strings, Cores, Dimensões

│       │   │

│       │   └── AndroidManifest.xml     # Permissões e configuração do App

│

└── build.gradle.kts              # Configurações do projeto (nível raiz)

----------------------------------------------------------------------------------------------------------------------------
💿 Como Executar o Projeto

  Para compilar e executar este projeto, você precisará do Android Studio.
  
  1. Pré-requisito: Ter o Android Studio instalado e configurado.
  
  2. Clonar o Repositório:

    git clone https://github.com/victorhjsantiago/doodlz.git

  3. Abrir no Android Studio:

     Abra o Android Studio.

     Selecione "Open" (ou "Open an Existing Project").

     Navegue até à pasta doodlz que você clonou e selecione-a.

  4. Sincronizar o Gradle:

     O Android Studio irá detetar o projeto. Confie no projeto, se solicitado.

     Aguarde o Gradle sincronizar e fazer o download de todas as dependências necessárias (o que deve ser rápido).

  5. Executar a Aplicação:

     Conecte um dispositivo Android físico (via USB, com depuração ativada) ou inicie um Emulador (AVD).

     Clique no botão "Run" (▶️) na barra de ferramentas do Android Studio.
