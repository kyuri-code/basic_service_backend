# Spring Boot ToDoアプリケーション

このアプリケーションは、Spring Bootを使用して構築されたシンプルなToDoアプリケーションです。RESTful APIを提供し、フロントエンドからのリクエストを処理してタスクやコメントを管理します。

## 目次

- [制約](#制約)
- [機能](#機能)
- [DB](#DB)
- [インストールとセットアップ](#インストールとセットアップ)
- [APIエンドポイント](#apiエンドポイント)
- [バックグラウンドでAPIサーバを起動](#バックグラウンドでAPIサーバを起動)
- [使用技術](#使用技術)
- [構成](#構成)

## 制約
Windowsで実施する場合は、WSL2のセットアップを完了するようにしてください。
プロジェクト用の端末を使用している場合は、使用しないようにしてください。
WSL2のセットアップはOS自体の設定を変更してしまうので、推奨しないです。
下記リンクが参考になると思います。Windows11ですが、10でも参考になるかと思います。

[WSL2の設定方法](https://note.com/hiro20180901/n/nc798a07485e2)

## 機能

- **タスクの作成**: 新しいタスクを作成するAPIエンドポイントを提供します。
- **タスクの更新**: 既存のタスクを更新するAPIエンドポイントを提供します。
- **タスクの削除**: タスクを削除するAPIエンドポイントを提供します（論理削除または物理削除）。
- **タスクの完了**: タスクを完了としてマークするAPIエンドポイントを提供します。
- **コメントの追加**: タスクにコメントを追加するAPIエンドポイントを提供します。
- **コメントの取得**: タスクに関連するコメントを取得するAPIエンドポイントを提供します。

## DB
DBはpostgreSQLを使用する
```bash
    # postgresqlのインストール
    sudo apt-get install postgresql

    # postgresqlの起動
    sudo systemctl start postgresql

    # インストール確認
    # バージョン情報が出力されたらインストールされていることになる
    # command not foundが出力されたらインストールされていないことになる
    psql --version

    # デフォルトでpostgresというスーパーユーザが作成される。
    # このユーザに切り開けてPostgreSQLシェルにログインする
    sudo -i -u postgres
    # PostgreSQLシェルにログイン
    psql
    # ----PosrtgreSQLシェル ここから----
    CREATE DATABASE sample;
    CREATE USER admin WITH ENCRYPTED PASSWORD 'Password!23';
    GRANT ALL PRIVILEGES ON DATABASE sample TO admin;
    # ----PosrtgreSQLシェル ここまで----
```

## インストールとセットアップ

このアプリケーションをローカルで実行するには、以下の手順に従ってください。

1. **JDKをWSL2にInstall**
```bash
    # パッケージリストのアップデート
    sudo apt-get update
    # openjdk21のインストール
    sudo apt-get install openjdk-21-jdk
    # インストレールされたか確認
    java --version

    # インストールしたjdkの格納場所
    # /usr/lib/jvm/java-21-openjdk-amd64/
    # 事前にパスの存在確認を行う
    # vimコマンドで環境変数を編集
    sudo vim /etc/environment
    # ----vimの編集画面 ここから----
    # 下記を追記する
    JAVA_HOME="/usr/lib/jvm/java-21-openjdk-amd64"
    # ----vimの編集画面 ここまで----

    # 環境変数の反映
    source /etc/environment

    # 確認
    echo $JAVA_HOME
```

2. **リポジトリのクローン**:
```bash
    git clone https://github.com/kyuri-code/basic_service_backend.git
    cd basic_service_backend
```

3. **依存関係のインストール**: Gradleを使用して依存関係をインストールします。
```bash
    ./gradlew clean build
```

4. **DBの接続情報の設定**:接続先情報は環境変数から読み取るため、exportコマンドで事前に設定しておく。
```yaml
spring:
application:
    name: demo
# url,usernamem,passwordは事前に環境変数に設定しておく
# デフォルトにはローカルで使用するものを採用している
datasource:
    url: ${DB_URL:jdbc:postgresql://localhost:5432/sample}
    username: ${DB_USERNAME:admin}
    password: ${DB_PASSWORD:Password!23}
    driver-class-name: org.postgresql.Driver
```

5. **起動方法** : VSCodeでAPIサーバの起動を確認

## バックグラウンドでAPIサーバを起動
毎度手動でAPIサーバを起動するのは面倒なので、自動で起動するような設定を行う。
自動設定を行うために、OSが持っているシステムのsystemdを使います。

`サービスファイルの作成`
```bash
    # サービスファイルを作成する
    # /etc/systemd/system/[MY_APP].serviceというファイルを作成する
    sudo vim /etc/systemd/system/myapi.service
    # ----vimの編集画面 ここから----
    [Unit]
    Description=My Spring Boot Application
    After=syslog.target

    [Service]
    User=root
    ExecStart=/usr/bin/java -jar [/path/to/your-app-name.jar] <-jarファイルの絶対パスを設定
    SuccessExitStatus=143
    Restart=always
    StandardOutput=syslog
    StandardError=syslog
    SyslogIdentifier=myapi

    [Install]
    WantedBy=multi-user.target

    # ----vimの編集画面 ここまで----

    # サービスの有効化
    sudo systemctl enbale myapi.service
    # サービスの起動
    sudo systemctl start myapi.service
    # サービスの起動確認
    # サービスステータスの出力が長くてプロンプトに戻れない場合は"q"を押すとプロンプトに戻れる
    sudo systemctl status myapi.service

    # 確認
    curl http://localhost:8080/api/tasks/healthcheck

    # 停止するとき
    sudo systemctl stop myapi.service
    # サービスの起動確認
    # Active:inactive(dead)となっていれば停止している
    sudo systemctl status myapi.service
```

## 使用技術
- **Java**: メインプログラミング言語
- **Spring Boot**: アプリケーションフレームワーク
- **PostgreSQL**: データベース
- **MyBatis**: ORマッパー
- **Gradle**: ビルドツール
- **Lombok**: Javaモデルクラスのボイラープレートコード削減

## 構成
```bash
    src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── study
    │   │           └── basic
    │   │               └── service
    │   │                   └── demo
    │   │                       ├── DemoApplication.java   # mainメソッド
    │   │                       ├── controller             # APIコントローラ
    │   │                       ├── model                  # エンティティモデル
    │   │                       ├── service                # ビジネスロジック
    │   │                       └── mapper                 # MyBatisマッパー
    │   └── resources
    │       ├── application.yaml  # アプリケーション設定
    │       ├── mapper
    │       │   └── TaskMapper.xml       # MyBatisマッパーXML
    │       └── static
    │           └── ...                 # 静的リソース 今回は未使用
    └── test
        └── java
            └── com
                └── study
                    └── basic
                        └── service
                            └── demo
                                └── ...         # テストコード
```

