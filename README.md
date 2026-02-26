<div align="center">

<img src="https://cdn-icons-png.flaticon.com/512/1046/1046874.png" alt="Doodlz Logo" width="110" />

# 🎨 Doodlz — Aplicativo de Desenho Android

**Um aplicativo de desenho nativo para Android, escrito em Java, que funciona como**
**uma tela de pintura digital com suporte a multi-toque, paleta de cores e salvamento de imagens.**

<br>

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Custom View](https://img.shields.io/badge/UI-Custom%20View-blueviolet?style=for-the-badge)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Status](https://img.shields.io/badge/Status-Completo_(Demo)-brightgreen?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

</div>

---

## 📚 Tabela de Conteúdos

> Navegue rapidamente pelas seções do projeto.

| # | Seção |
|:-:|:------|
| 1 | [📖 Sobre o Projeto](#-sobre-o-projeto) |
| 2 | [✨ Funcionalidades Principais](#-funcionalidades-principais) |
| 3 | [🛠️ Pilha de Tecnologias](#️-pilha-de-tecnologias) |
| 4 | [🔑 Destaques da Implementação](#-destaques-da-implementação) |
| 5 | [📂 Estrutura do Repositório](#-estrutura-do-repositório) |
| 6 | [🚀 Como Executar](#-como-executar) |
| 7 | [🤝 Como Contribuir](#-como-contribuir) |
| 8 | [👨‍💻 Autor](#-autor) |
| 9 | [📄 Licença](#-licença) |

---

## 📖 Sobre o Projeto

> **Doodlz** é uma aplicação de desenho nativa para Android que transforma a tela do dispositivo em uma **tela de pintura digital** totalmente interativa.

O coração do projeto é uma **View personalizada** (`DoodleView`) que captura e renderiza os movimentos dos dedos em tempo real, com suporte completo a **multi-toque** — permitindo desenhar com vários dedos simultaneamente.

Além da experiência de desenho, o app inclui um menu de ferramentas completo e integração direta com o **hardware do dispositivo**: basta agitar o celular para limpar a tela, utilizando o acelerômetro nativo do Android.

---

## ✨ Funcionalidades Principais

| Ícone | Funcionalidade | Descrição |
|:-----:|:---------------|:----------|
| ✍️ | **Desenho Multi-Touch** | Desenhe com vários dedos ao mesmo tempo. Cada toque é rastreado com um `Path` individual e independente. |
| 🎨 | **Seletor de Cores** | `ColorDialogFragment` com `RecyclerView` exibindo uma paleta de cores completa para o pincel. |
| 〰️ | **Seletor de Espessura** | `LineWidthDialogFragment` com `SeekBar` para ajuste preciso da espessura da linha em tempo real. |
| 💾 | **Salvar Desenho** | Salva a imagem atual diretamente na galeria do dispositivo via `MediaStore`. |
| 🖨️ | **Imprimir** | Envia o desenho para o serviço de impressão nativo do Android. |
| 🗑️ | **Apagar com Confirmação** | `EraseImageDialogFragment` solicita confirmação antes de limpar a tela para evitar perdas acidentais. |
| 📳 | **Apagar ao Agitar** | Usa o **Acelerômetro** (`Sensor.TYPE_ACCELEROMETER`) para detectar o gesto de "shake" e apagar automaticamente. |

---

## 🛠️ Pilha de Tecnologias

| Tecnologia | Função no Projeto |
|:-----------|:------------------|
| **Java** | Linguagem principal de toda a lógica do aplicativo. |
| **Android SDK** | Framework nativo para desenvolvimento Android. |
| **Arquitetura Fragmentos + Atividade Única** | `MainActivity` hospeda o `DoodleFragment` como controlador principal. |
| **Custom View (`DoodleView`)** | View personalizada que contém todo o motor de desenho 2D. |
| **Bitmap / Canvas / Paint / Path** | APIs nativas de gráficos 2D do Android para renderização dos traços. |
| **SensorManager** | Acesso ao Acelerômetro para detecção do gesto de agitar. |
| **MediaStore** | API do Android para salvar imagens na galeria do dispositivo. |
| **AndroidManifest.xml** | Declaração de permissões (`WRITE_EXTERNAL_STORAGE`) e configuração do app. |
| **Gradle (Kotlin DSL)** | Sistema de build e gestão de dependências do projeto. |

---

## 🔑 Destaques da Implementação

### 🖌️ DoodleView.java — A Tela de Pintura Multi-Touch

> O núcleo de todo o projeto. `DoodleView` é uma `View` personalizada que gerencia toda a lógica de desenho em tempo real.

| Componente | Tipo | Responsabilidade |
|:-----------|:----:|:-----------------|
| `bitmap` | `Bitmap` | Tela de fundo onde os traços são persistidos entre redesenhos. |
| `bitmapCanvas` | `Canvas` | Canvas associado ao `Bitmap`, onde o `Paint` efetivamente desenha. |
| `pathMap` | `HashMap<Integer, Path>` | Armazena o traço (`Path`) de cada dedo, identificado pelo `pointerId`. |
| `previousPointMap` | `HashMap<Integer, Point>` | Guarda o ponto anterior de cada dedo para gerar linhas suaves e contínuas. |

**Eventos de toque processados pelo `onTouchEvent`:**

```java
// Cada evento é tratado individualmente para suportar múltiplos dedos
switch (action) {
    case MotionEvent.ACTION_DOWN:        // Primeiro dedo toca a tela
    case MotionEvent.ACTION_POINTER_DOWN: // Dedo adicional toca a tela
    case MotionEvent.ACTION_MOVE:        // Qualquer dedo se move
    case MotionEvent.ACTION_UP:          // Último dedo sai da tela
    case MotionEvent.ACTION_POINTER_UP:  // Um dedo adicional sai da tela
}
```

---

### 📳 SensorEventListenerHelper.java — Apagar ao Agitar

> Esta classe encapsula toda a lógica do acelerômetro, mantendo o `DoodleFragment` limpo e com responsabilidade única.

```java
// Lógica de detecção do gesto de "shake"
float acceleration = /* cálculo da aceleração resultante */;

if (acceleration > SHAKE_THRESHOLD) {
    // Invoca eraseImage() no DoodleFragment
    doodleFragment.eraseImage();
}
```

| Item | Detalhe |
|:-----|:--------|
| **Sensor utilizado** | `Sensor.TYPE_ACCELEROMETER` |
| **Threshold** | Constante `SHAKE_THRESHOLD` — define a sensibilidade do gesto. |
| **Ação disparada** | Chama `eraseImage()` no `DoodleFragment` quando o threshold é excedido. |

---

## 📂 Estrutura do Repositório

```plaintext
doodlz/
│
├── 📄 build.gradle.kts                        # ⚙️  Configurações do projeto (nível raiz)
│
└── 📁 app/
    ├── 📄 build.gradle.kts                    # ⚙️  Configurações do módulo 'app'
    │
    └── 📁 src/main/
        │
        ├── 📄 AndroidManifest.xml             # 🔐 Permissões e configuração do app
        │
        ├── 📁 java/com/example/doodlz/
        │   ├── 📄 MainActivity.java           # 🏠 Atividade principal (Host)
        │   ├── 📄 DoodleFragment.java         # 🎛️  Fragmento principal (Controlador)
        │   ├── 📄 DoodleView.java             # 🖌️  Motor de desenho Multi-Touch ← CORE
        │   ├── 📄 SensorEventListenerHelper.java # 📳 Lógica do Acelerômetro ← CORE
        │   ├── 📄 ColorDialogFragment.java    # 🎨 Dialog de seleção de cor
        │   ├── 📄 LineWidthDialogFragment.java # 〰️  Dialog de espessura da linha
        │   └── 📄 EraseImageDialogFragment.java # 🗑️  Dialog de confirmação de apagar
        │
        └── 📁 res/
            ├── 📁 layout/                     # 🖼️  Layouts XML das telas e dialogs
            │   ├── 📄 activity_main.xml
            │   ├── 📄 fragment_doodle.xml
            │   ├── 📄 fragment_color.xml
            │   └── 📄 fragment_line_width.xml
            ├── 📁 drawable/                   # 🎭 Ícones e vetores do app
            └── 📁 values/                     # 📝 Strings, Cores e Dimensões
```

---

## 🚀 Como Executar

### 📋 Pré-requisitos

| Requisito | Detalhe |
|:----------|:--------|
| **Android Studio** | Versão **Hedgehog** ou superior, instalada e configurada. |
| **JDK** | Versão **11 ou superior** (geralmente incluído no Android Studio). |
| **Dispositivo ou Emulador** | Android físico (USB + depuração ativada) ou AVD configurado. |

---

### 🔧 Passo a Passo

**1. Clone o repositório:**

```bash
git clone https://github.com/VictorHJesusSantiago/doodlz.git
```

**2. Abra no Android Studio:**

```
Android Studio → File → Open → Selecione a pasta 'doodlz'
```

**3. Sincronize o Gradle:**

> O Android Studio detectará o projeto automaticamente. Aguarde a sincronização das dependências — o processo é rápido, pois o projeto não possui bibliotecas externas.

```
Build → Sync Project with Gradle Files
```

**4. Execute a aplicação:**

```
Run → Run 'app'  (ou clique no botão ▶️ na barra de ferramentas)
```

---

### 📱 Testando Funcionalidades de Hardware

| Funcionalidade | Como Testar |
|:---------------|:------------|
| 🎨 **Desenho Multi-Touch** | Em dispositivo físico, use múltiplos dedos simultaneamente. |
| 📳 **Apagar ao Agitar** | Agite o dispositivo físico. No emulador, use `Extended Controls → Virtual sensors`. |
| 💾 **Salvar na Galeria** | Conceda a permissão `WRITE_EXTERNAL_STORAGE` quando solicitada. |

---

## 🤝 Como Contribuir

> Contribuições são muito bem-vindas! Siga as etapas abaixo para colaborar de forma organizada.

| Passo | Ação | Comando |
|:-----:|:-----|:--------|
| 1️⃣ | **Fork** | Crie um fork do repositório para a sua conta. | — |
| 2️⃣ | **Branch** | Crie sua feature branch a partir da `main`. | `git checkout -b feature/NovaFeature` |
| 3️⃣ | **Commit** | Salve as alterações com mensagem clara e semântica. | `git commit -m 'feat: Adiciona NovaFeature'` |
| 4️⃣ | **Push** | Envie a branch para o repositório remoto. | `git push origin feature/NovaFeature` |
| 5️⃣ | **Pull Request** | Abra um PR detalhando as mudanças realizadas. | — |

<div align="center">

<br>

**Se este projeto foi útil para os seus estudos, deixe uma estrela ⭐️ no repositório!**

</div>

---

## 👨‍💻 Autor

<div align="center">

<br>

**Victor H. J. Santiago**

<br>

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/VictorHJesusSantiago)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/victor-henrique-de-jesus-santiago/)

</div>

---

## 📄 Licença

<div align="center">

Este projeto está distribuído sob a **Licença MIT**.
Consulte o arquivo [`LICENSE`](./LICENSE) no repositório para mais informações.

![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

</div>

---

<div align="center">

*Feito com 🎨 e Java por **Victor H. J. Santiago***

</div>
