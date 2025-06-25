# Guia de Instalação Técnica - Android Music Player

## Índice
1. [Requisitos do Sistema](#requisitos-do-sistema)
2. [Configuração do Ambiente](#configuração-do-ambiente)
3. [Instalação das Dependências](#instalação-das-dependências)
4. [Compilação do Projeto](#compilação-do-projeto)
5. [Configuração de Assinatura](#configuração-de-assinatura)
6. [Deployment](#deployment)
7. [Testes](#testes)
8. [Troubleshooting](#troubleshooting)

## Requisitos do Sistema

### Ambiente de Desenvolvimento

**Sistema Operativo**
- Windows 10/11 (64-bit)
- macOS 10.14+ (Mojave ou superior)
- Linux Ubuntu 18.04+ ou distribuições equivalentes

**Hardware Mínimo**
- RAM: 8GB (recomendado 16GB)
- Armazenamento: 10GB livres
- Processador: Intel i5 ou AMD equivalente

**Software Necessário**
- Android Studio Arctic Fox (2020.3.1) ou superior
- JDK 11 ou superior
- Git 2.0+
- Gradle 7.0+

### Dispositivos de Teste

**Android Mínimo**
- API Level 21 (Android 5.0)
- RAM: 2GB
- Armazenamento: 1GB livre

**Android Recomendado**
- API Level 30+ (Android 11+)
- RAM: 4GB+
- Armazenamento: 2GB+ livre

## Configuração do Ambiente

### 1. Instalação do Android Studio

**Download e Instalação**
```bash
# Linux (Ubuntu/Debian)
sudo snap install android-studio --classic

# macOS (via Homebrew)
brew install --cask android-studio

# Windows
# Descarregue de https://developer.android.com/studio
```

**Configuração Inicial**
1. Abra o Android Studio
2. Complete o setup wizard
3. Instale Android SDK Platform 21-34
4. Configure Android Virtual Device (AVD)

### 2. Configuração do JDK

**Verificar Versão**
```bash
java -version
javac -version
```

**Instalar JDK 11 (se necessário)**
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-11-jdk

# macOS
brew install openjdk@11

# Windows
# Descarregue de https://adoptopenjdk.net/
```

**Configurar JAVA_HOME**
```bash
# Linux/macOS (.bashrc ou .zshrc)
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export PATH=$PATH:$JAVA_HOME/bin

# Windows (Variáveis de Ambiente)
JAVA_HOME=C:\Program Files\Java\jdk-11.0.x
PATH=%PATH%;%JAVA_HOME%\bin
```

### 3. Configuração do Android SDK

**Localização Padrão**
```bash
# Linux/macOS
~/Android/Sdk

# Windows
%LOCALAPPDATA%\Android\Sdk
```

**Configurar ANDROID_HOME**
```bash
# Linux/macOS
export ANDROID_HOME=~/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools

# Windows
ANDROID_HOME=%LOCALAPPDATA%\Android\Sdk
PATH=%PATH%;%ANDROID_HOME%\tools;%ANDROID_HOME%\platform-tools
```

## Instalação das Dependências

### 1. Clone do Repositório

```bash
# Clone o projeto
git clone https://github.com/seu-usuario/android-music-player.git
cd android-music-player

# Verificar estrutura
ls -la
```

### 2. Configuração do Gradle

**gradle.properties**
```properties
# Configurações de performance
org.gradle.jvmargs=-Xmx4096m -XX:MaxPermSize=512m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8
org.gradle.parallel=true
org.gradle.configureondemand=true
org.gradle.daemon=true

# Configurações Android
android.useAndroidX=true
android.enableJetifier=true

# Configurações de build
android.enableR8.fullMode=true
android.enableBuildCache=true
```

**local.properties**
```properties
# Localização do SDK (ajustar conforme necessário)
sdk.dir=/home/user/Android/Sdk

# Configurações de assinatura (opcional para debug)
keystore.path=debug.keystore
keystore.password=android
key.alias=androiddebugkey
key.password=android
```

### 3. Sincronização de Dependências

```bash
# Via linha de comando
./gradlew build --refresh-dependencies

# Ou no Android Studio
# File > Sync Project with Gradle Files
```

### 4. Dependências Específicas

**FFmpeg Mobile**
```gradle
// No app/build.gradle
implementation 'com.arthenica:mobile-ffmpeg-full:4.4.LTS'

// Configurações específicas
android {
    packagingOptions {
        pickFirst '**/libc++_shared.so'
        pickFirst '**/libjsc.so'
    }
}
```

**ExoPlayer**
```gradle
implementation 'com.google.android.exoplayer:exoplayer:2.19.1'
implementation 'com.google.android.exoplayer:exoplayer-ui:2.19.1'
```

## Compilação do Projeto

### 1. Build de Debug

```bash
# Compilação completa
./gradlew assembleDebug

# Instalação direta no dispositivo
./gradlew installDebug

# Build e instalação
./gradlew build installDebug
```

### 2. Build de Release

```bash
# Compilação de release
./gradlew assembleRelease

# Build com testes
./gradlew build

# Limpeza antes do build
./gradlew clean assembleRelease
```

### 3. Configurações de Build

**build.gradle (app)**
```gradle
android {
    compileSdk 34
    
    defaultConfig {
        applicationId "com.example.androidmusicplayer"
        minSdk 21
        targetSdk 34
        versionCode 1
        versionName "1.0.0"
        
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        
        // Configurações específicas
        multiDexEnabled true
        vectorDrawables.useSupportLibrary = true
    }
    
    buildTypes {
        debug {
            debuggable true
            minifyEnabled false
            applicationIdSuffix ".debug"
            versionNameSuffix "-debug"
        }
        
        release {
            minifyEnabled true
            shrinkResources true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            
            // Configurações de assinatura
            signingConfig signingConfigs.release
        }
    }
    
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_11
        targetCompatibility JavaVersion.VERSION_11
    }
    
    kotlinOptions {
        jvmTarget = '11'
    }
    
    buildFeatures {
        compose true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion compose_version
    }
}
```

## Configuração de Assinatura

### 1. Criação de Keystore

```bash
# Criar keystore para release
keytool -genkey -v -keystore release-key.keystore -alias release -keyalg RSA -keysize 2048 -validity 10000

# Verificar keystore
keytool -list -v -keystore release-key.keystore
```

### 2. Configuração no Gradle

**gradle.properties**
```properties
# Configurações de assinatura
RELEASE_STORE_FILE=release-key.keystore
RELEASE_STORE_PASSWORD=sua_senha_keystore
RELEASE_KEY_ALIAS=release
RELEASE_KEY_PASSWORD=sua_senha_key
```

**build.gradle (app)**
```gradle
android {
    signingConfigs {
        release {
            storeFile file(RELEASE_STORE_FILE)
            storePassword RELEASE_STORE_PASSWORD
            keyAlias RELEASE_KEY_ALIAS
            keyPassword RELEASE_KEY_PASSWORD
        }
    }
    
    buildTypes {
        release {
            signingConfig signingConfigs.release
            // outras configurações...
        }
    }
}
```

### 3. Build Assinado

```bash
# Build de release assinado
./gradlew assembleRelease

# Verificar assinatura
jarsigner -verify -verbose -certs app/build/outputs/apk/release/app-release.apk
```

## Deployment

### 1. Google Play Store

**Preparação**
1. Crie conta de desenvolvedor Google Play
2. Configure app bundle em vez de APK
3. Prepare metadados e screenshots

**Build do App Bundle**
```bash
# Gerar App Bundle
./gradlew bundleRelease

# Localização do arquivo
app/build/outputs/bundle/release/app-release.aab
```

**Upload**
1. Acesse Google Play Console
2. Crie nova aplicação
3. Upload do App Bundle
4. Complete informações da loja
5. Submeta para revisão

### 2. Distribuição Direta

**APK Assinado**
```bash
# Build APK para distribuição
./gradlew assembleRelease

# Localização
app/build/outputs/apk/release/app-release.apk
```

**Verificações Finais**
- Teste em múltiplos dispositivos
- Verifique permissões
- Confirme funcionalidades offline
- Teste performance

### 3. CI/CD (Opcional)

**GitHub Actions**
```yaml
name: Android CI

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 11
      uses: actions/setup-java@v3
      with:
        java-version: '11'
        distribution: 'temurin'
    
    - name: Cache Gradle packages
      uses: actions/cache@v3
      with:
        path: |
          ~/.gradle/caches
          ~/.gradle/wrapper
        key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}
        restore-keys: |
          ${{ runner.os }}-gradle-
    
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
    
    - name: Build with Gradle
      run: ./gradlew build
    
    - name: Run tests
      run: ./gradlew test
```

## Testes

### 1. Testes Unitários

```bash
# Executar todos os testes
./gradlew test

# Testes específicos
./gradlew testDebugUnitTest

# Com relatórios
./gradlew test --continue
```

### 2. Testes de Instrumentação

```bash
# Testes no dispositivo
./gradlew connectedAndroidTest

# Testes específicos
./gradlew connectedDebugAndroidTest
```

### 3. Testes de UI

```bash
# Testes Espresso
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.example.androidmusicplayer.ui.PlayerScreenTest
```

### 4. Análise de Código

```bash
# Lint check
./gradlew lint

# Detekt (análise estática Kotlin)
./gradlew detekt

# Relatórios de cobertura
./gradlew jacocoTestReport
```

## Troubleshooting

### Problemas Comuns

**Gradle Sync Failed**
```bash
# Limpar cache
./gradlew clean

# Invalidar cache do Android Studio
# File > Invalidate Caches and Restart

# Verificar versões no gradle/wrapper/gradle-wrapper.properties
```

**OutOfMemoryError**
```bash
# Aumentar heap size no gradle.properties
org.gradle.jvmargs=-Xmx8192m -XX:MaxPermSize=512m

# No Android Studio
# Help > Edit Custom VM Options
-Xmx8192m
```

**Dependências Conflituosas**
```bash
# Verificar árvore de dependências
./gradlew app:dependencies

# Resolver conflitos no build.gradle
configurations.all {
    resolutionStrategy {
        force 'androidx.core:core:1.8.0'
    }
}
```

**Problemas de Assinatura**
```bash
# Verificar configurações
./gradlew signingReport

# Debug keystore corrompido
rm ~/.android/debug.keystore
# Android Studio irá recriar automaticamente
```

**Problemas de Performance**
```bash
# Profile build
./gradlew assembleDebug --profile

# Usar build cache
org.gradle.caching=true

# Parallel builds
org.gradle.parallel=true
```

### Logs e Debug

**Logs do Gradle**
```bash
# Build verbose
./gradlew assembleDebug --info

# Debug completo
./gradlew assembleDebug --debug

# Stack trace completo
./gradlew assembleDebug --stacktrace
```

**Logs do Android**
```bash
# Logs em tempo real
adb logcat

# Filtrar por aplicação
adb logcat | grep "com.example.androidmusicplayer"

# Logs específicos
adb logcat -s "MusicPlayer"
```

### Recursos Adicionais

**Documentação Oficial**
- [Android Developer Guide](https://developer.android.com/guide)
- [Gradle Build Tool](https://gradle.org/guides/)
- [Kotlin Documentation](https://kotlinlang.org/docs/)

**Ferramentas Úteis**
- [APK Analyzer](https://developer.android.com/studio/build/apk-analyzer)
- [Layout Inspector](https://developer.android.com/studio/debug/layout-inspector)
- [Memory Profiler](https://developer.android.com/studio/profile/memory-profiler)

**Comunidade**
- [Stack Overflow](https://stackoverflow.com/questions/tagged/android)
- [Reddit r/androiddev](https://reddit.com/r/androiddev)
- [Android Developers Discord](https://discord.gg/android-developers)

---

**Para suporte técnico adicional, consulte a documentação oficial ou contacte a equipa de desenvolvimento.**

