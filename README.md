# Spring Boot ToDoアプリケーション

このアプリケーションは、Spring Bootを使用して構築されたシンプルなToDoアプリケーションです。RESTful APIを提供し、フロントエンドからのリクエストを処理してタスクやコメントを管理します。

## 目次

- [機能](#機能)
- [インストールとセットアップ](#インストールとセットアップ)
- [APIエンドポイント](#apiエンドポイント)
- [使用技術](#使用技術)
- [構成](#構成)

## 機能

- **タスクの作成**: 新しいタスクを作成するAPIエンドポイントを提供します。
- **タスクの更新**: 既存のタスクを更新するAPIエンドポイントを提供します。
- **タスクの削除**: タスクを削除するAPIエンドポイントを提供します（論理削除または物理削除）。
- **タスクの完了**: タスクを完了としてマークするAPIエンドポイントを提供します。
- **コメントの追加**: タスクにコメントを追加するAPIエンドポイントを提供します。
- **コメントの取得**: タスクに関連するコメントを取得するAPIエンドポイントを提供します。

## インストールとセットアップ

このアプリケーションをローカルで実行するには、以下の手順に従ってください。

1. **リポジトリのクローン**:
   ```bash
   git clone https://github.com/kyuri-code/basic_service_backend.git
   cd springboot-todo-app
   ```

2. **依存関係のインストール**: Gradleを使用して依存関係をインストールします。
   ```bash
   ./gradlew clean build
   ```

3. **DBの接続情報の設定**:接続先情報は環境変数から読み取るため、exportコマンドで事前に設定しておく。
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

4. **起動方法**
起動方法やデプロイ方法は別資料を参照

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