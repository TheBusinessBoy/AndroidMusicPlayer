# Android Music Player - Leitor de Música Avançado

Um leitor de música Android completo e moderno com funcionalidades avançadas de reprodução, conversão de ficheiros, download de conteúdo e personalização de temas.

## 🎵 Características Principais

### Reprodução de Áudio/Vídeo
- Suporte para todos os formatos de áudio populares (MP3, AAC, FLAC, WAV, OGG, M4A)
- Reprodução de vídeo com extração de áudio (MP4, AVI, MKV, MOV, WMV, FLV, WEBM)
- Controles de reprodução intuitivos com visualizações animadas
- Sistema de fila de reprodução avançado
- Modo aleatório e repetição

### Equalizador e Efeitos Sonoros
- Equalizador gráfico de 5 bandas totalmente personalizável
- Presets de equalização (Rock, Pop, Jazz, Clássica, Electrónica, etc.)
- Bass Booster e Virtualizer integrados
- Efeitos 3D e som surround
- Controlo de volume e amplificação

### Conversão de Ficheiros
- Conversão entre formatos de áudio (MP3, AAC, FLAC, WAV, OGG, M4A)
- Extração de áudio de ficheiros de vídeo
- Múltiplas opções de qualidade (Baixa, Média, Alta, Lossless)
- Conversão em lote
- Corte e edição básica de áudio

### Sistema de Download
- Download da música atualmente em reprodução
- Download a partir de URLs de serviços de streaming
- Gestor de downloads com controlo de progresso
- Pausa, retoma e cancelamento de downloads
- Organização automática de ficheiros descarregados

### Temas e Personalização
- 6 temas visuais distintos (Default, Sunset, Nature, Ocean, Fire, Blossom)
- Modo automático baseado na hora do dia
- Suporte para cores dinâmicas (Android 12+)
- Interface adaptativa para tablets e telefones
- Animações fluidas e transições suaves

## 📱 Compatibilidade

- **Versões Android**: 5.0 (API 21) até Android 14+ (API 34)
- **Dispositivos**: Telefones e tablets Android
- **Arquiteturas**: ARM64, ARM32, x86, x86_64
- **Tamanhos de ecrã**: Desde 4" até tablets de 12"

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Kotlin
- **UI Framework**: Jetpack Compose
- **Arquitetura**: MVVM com Repository Pattern
- **Base de Dados**: Room Database
- **Reprodução de Média**: ExoPlayer
- **Conversão de Ficheiros**: FFmpeg Mobile
- **Downloads**: Android DownloadManager + OkHttp
- **Efeitos de Áudio**: Android AudioEffect API
- **Navegação**: Jetpack Navigation Compose

## 📦 Estrutura do Projeto

```
android_music_player/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/androidmusicplayer/
│   │   │   ├── ui/
│   │   │   │   ├── screens/          # Ecrãs da aplicação
│   │   │   │   ├── components/       # Componentes reutilizáveis
│   │   │   │   ├── theme/           # Sistema de temas
│   │   │   │   └── viewmodel/       # ViewModels
│   │   │   ├── service/             # Serviços de reprodução
│   │   │   ├── repository/          # Repositórios de dados
│   │   │   ├── converter/           # Sistema de conversão
│   │   │   ├── downloader/          # Sistema de downloads
│   │   │   └── audio/               # Controladores de áudio
│   │   ├── res/                     # Recursos da aplicação
│   │   └── AndroidManifest.xml
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle
├── settings.gradle
└── README.md
```

## 🚀 Instalação e Configuração

### Pré-requisitos
- Android Studio Arctic Fox ou superior
- JDK 11 ou superior
- Android SDK 21 ou superior
- Gradle 7.0 ou superior

### Passos de Instalação

1. **Clone o repositório**
   ```bash
   git clone https://github.com/seu-usuario/android-music-player.git
   cd android-music-player
   ```

2. **Abra no Android Studio**
   - Abra o Android Studio
   - Selecione "Open an existing project"
   - Navegue até à pasta do projeto e selecione

3. **Sincronize as dependências**
   - O Android Studio irá automaticamente sincronizar as dependências
   - Aguarde a conclusão do processo

4. **Configure o dispositivo**
   - Conecte um dispositivo Android ou configure um emulador
   - Ative a depuração USB no dispositivo

5. **Execute a aplicação**
   - Clique no botão "Run" no Android Studio
   - Selecione o dispositivo de destino
   - Aguarde a instalação e execução

## 📋 Dependências Principais

```gradle
dependencies {
    // Jetpack Compose
    implementation "androidx.compose.ui:ui:$compose_version"
    implementation "androidx.compose.material:material:$compose_version"
    implementation "androidx.activity:activity-compose:1.7.2"
    
    // ExoPlayer para reprodução de média
    implementation "com.google.android.exoplayer:exoplayer:2.19.1"
    
    // FFmpeg para conversão de ficheiros
    implementation "com.arthenica:mobile-ffmpeg-full:4.4.LTS"
    
    // Room para base de dados
    implementation "androidx.room:room-runtime:2.5.0"
    implementation "androidx.room:room-ktx:2.5.0"
    
    // ViewModel e LiveData
    implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1"
    
    // Navegação
    implementation "androidx.navigation:navigation-compose:2.6.0"
    
    // Coroutines
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1"
    
    // OkHttp para downloads
    implementation "com.squareup.okhttp3:okhttp:4.11.0"
}
```

## 🎯 Funcionalidades Detalhadas

### Ecrã Principal do Reprodutor
- Visualização circular animada da música
- Controles de reprodução com animações fluidas
- Barra de progresso interativa
- Informações da música (título, artista, álbum)
- Botões de ação (favorito, partilha, download)

### Biblioteca de Música
- Navegação por separadores (Faixas, Álbuns, Artistas, Pastas)
- Pesquisa avançada com filtros
- Ordenação personalizável
- Criação e gestão de playlists
- Visualização em grelha e lista

### Equalizador Avançado
- 5 bandas de frequência ajustáveis
- 10+ presets profissionais
- Bass Booster com controlo de intensidade
- Virtualizer para efeito 3D
- Reverb com múltiplos ambientes

### Conversor de Ficheiros
- Interface intuitiva de seleção de ficheiros
- Múltiplos formatos de saída
- Opções de qualidade detalhadas
- Fila de conversão com progresso
- Pré-visualização de metadados

### Gestor de Downloads
- Download direto da música em reprodução
- Suporte para URLs de streaming
- Controlo completo de downloads (pausa/retoma/cancelar)
- Organização automática por artista/álbum
- Notificações de progresso

## 🎨 Sistema de Temas

### Temas Disponíveis

1. **Default (Roxo/Azul)**
   - Cores primárias: #6200EE, #03DAC5
   - Gradiente: Roxo escuro para azul

2. **Sunset (Laranja/Âmbar)**
   - Cores primárias: #FF6B35, #FFAB40
   - Gradiente: Laranja quente para âmbar

3. **Nature (Verde/Floresta)**
   - Cores primárias: #4CAF50, #8BC34A
   - Gradiente: Verde escuro para floresta

4. **Ocean (Azul/Ciano)**
   - Cores primárias: #2196F3, #03DAC5
   - Gradiente: Azul oceano para ciano

5. **Fire (Vermelho/Carmesim)**
   - Cores primárias: #F44336, #FF5722
   - Gradiente: Vermelho fogo para carmesim

6. **Blossom (Rosa/Cor-de-rosa)**
   - Cores primárias: #E91E63, #FF4081
   - Gradiente: Rosa escuro para cor-de-rosa

### Funcionalidades de Tema
- Mudança automática baseada na hora
- Cores dinâmicas do sistema (Android 12+)
- Pré-visualização em tempo real
- Persistência de preferências

## 🔧 Configurações Avançadas

### Configurações de Áudio
- Qualidade de reprodução
- Crossfade entre faixas
- Normalização de volume
- Saída de áudio preferida

### Configurações de Download
- Diretório de destino
- Qualidade padrão
- Rede permitida (WiFi/Dados móveis)
- Downloads automáticos

### Configurações de Interface
- Animações e transições
- Tamanho da fonte
- Densidade de informação
- Modo noturno automático

## 📱 Suporte Multi-dispositivo

### Telefones
- Layout otimizado para ecrãs pequenos
- Navegação por gestos
- Controles acessíveis com uma mão

### Tablets
- Layout de duas colunas
- Navegação lateral permanente
- Aproveitamento do espaço extra

### Orientação Paisagem
- Controles reorganizados
- Visualização expandida
- Melhor aproveitamento do ecrã

## 🔒 Permissões Necessárias

```xml
<!-- Permissões obrigatórias -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<!-- Permissões para funcionalidades avançadas -->
<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
```

## 🐛 Resolução de Problemas

### Problemas Comuns

**Música não reproduz**
- Verifique as permissões de armazenamento
- Confirme que o formato é suportado
- Reinicie a aplicação

**Conversão falha**
- Verifique o espaço de armazenamento
- Confirme que o ficheiro não está corrompido
- Tente um formato diferente

**Download não funciona**
- Verifique a ligação à internet
- Confirme as permissões de armazenamento
- Verifique se o URL é válido

### Logs de Debug
Para ativar logs detalhados, adicione ao `build.gradle`:
```gradle
buildTypes {
    debug {
        debuggable true
        buildConfigField "boolean", "DEBUG_MODE", "true"
    }
}
```

## 📄 Licença

Este projeto está licenciado sob a Licença MIT - veja o ficheiro [LICENSE](LICENSE) para detalhes.

## 🤝 Contribuições

Contribuições são bem-vindas! Por favor:

1. Faça um fork do projeto
2. Crie uma branch para a sua funcionalidade (`git checkout -b feature/nova-funcionalidade`)
3. Commit as suas alterações (`git commit -am 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

## 📞 Suporte

Para suporte técnico ou questões:
- Abra uma issue no GitHub
- Envie email para: suporte@musicplayer.com
- Consulte a documentação completa

## 🔄 Atualizações

### Versão 1.0.0 (Atual)
- Lançamento inicial
- Todas as funcionalidades principais implementadas
- Suporte para Android 5.0+

### Próximas Versões
- Integração com serviços de streaming
- Sincronização na nuvem
- Widgets para ecrã inicial
- Suporte para Android Auto

---

**Desenvolvido com ❤️ para amantes de música**

