<template>
  <v-container>
    <template>
      <v-alert
        :value="alert"
        type="error"
        dark
        transition="scale-transition"
        dismissible
      >
        {{ alerttext }}
      </v-alert>
    </template>
    <v-data-table
      dense
      :headers="headers"
      :items="datalists"
      :items-per-page="10"
      :search="search"
      sort-by="userid"
      class="elevation-9"
      :footer-props="{
        showFirstLastPage: true,
        firstIcon: 'mdi-arrow-collapse-left',
        lastIcon: 'mdi-arrow-collapse-right',
        prevIcon: 'mdi-minus',
        nextIcon: 'mdi-plus',
        'items-per-page-text': '表示件数'
      }"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>
            <v-icon x-large>
              mdi-access-point
            </v-icon>
            MACアドレス管理
          </v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Search"
            class="mx-4"
            hide-details
          ></v-text-field>
          <v-spacer></v-spacer>
          <v-dialog v-model="dialog" max-width="400px">
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                fab
                color="primary"
                small
                dark
                class="mx-2"
                v-bind="attrs"
                v-on="on"
              >
                <v-icon>mdi-plus</v-icon>
              </v-btn>
            </template>
            <v-form v-model="valid">
              <v-card outlined>
                <v-list-item two-line>
                  <v-list-item-content>
                    <v-list-item-title class="headline mb-1">
                      <span class="headline">{{ formTitle }}</span>
                    </v-list-item-title>
                    <v-list-item-subtitle>
                      必要事項を入力し、保存ボタンを押して下さい。
                    </v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
                <v-card-text>
                  <v-container>
                    <v-row>
                      <v-col cols="12">
                        <v-text-field
                          @keydown.space.prevent
                          v-model.trim="editedItem.userid"
                          label="ユーザーID"
                          :rules="useridRules"
                          :counter="5"
                          required
                          outlined
                          placeholder="00000"
                        >
                        </v-text-field>
                      </v-col>
                      <v-col cols="12">
                        <v-text-field
                          @keydown.space.prevent
                          v-model.trim="editedItem.macaddress"
                          label="MACアドレス"
                          :rules="macaddressRules"
                          :counter="12"
                          required
                          outlined
                          :disabled="isDisabled"
                        >
                        </v-text-field>
                      </v-col>
                      <v-col cols="12">
                        <v-text-field
                          v-model.trim="editedItem.biko"
                          label="備考（PC名・補足情報等）"
                          :rules="bikoRules"
                          :counter="20"
                          required
                          outlined
                          placeholder="ARSXXX"
                        >
                        </v-text-field>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-card-text>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" text @click="close">
                    キャンセル
                  </v-btn>
                  <v-btn
                    color="blue darken-1"
                    text
                    @click="save"
                    :disabled="!valid"
                  >
                    保存
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-form>
          </v-dialog>
          <v-dialog v-model="dialogDelete" max-width="400px">
            <v-card>
              <v-card-title class="headline">
                削除してもよろしいですか？
              </v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="closeDelete">
                  キャンセル
                </v-btn>
                <v-btn color="blue darken-1" text @click="deleteItemConfirm">
                  削除
                </v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-icon small class="mr-2" @click="editItem(item)">
          mdi-pencil
        </v-icon>
        <v-icon small @click="deleteItem(item)">
          mdi-delete
        </v-icon>
      </template>
      <template v-slot:no-data>
        <v-btn color="primary">
          Reset
        </v-btn>
      </template>
    </v-data-table>
    <template>
      <v-snackbar v-model="snackbar" :timeout="sbtimeout">
        {{ sbtext }}
        <template v-slot:action="{ attrs }">
          <v-btn color="blue" text v-bind="attrs" @click="snackbar = false">
            Close
          </v-btn>
        </template>
      </v-snackbar>
    </template>
  </v-container>
</template>
<script>
import axios from "axios";
export default {
  data: () => ({
    dialog: false,
    dialogDelete: false,
    headers: [
      { text: "ユーザーID", align: "start", value: "userid" },
      { text: "MACアドレス", align: "start", value: "macaddress" },
      { text: "備考", align: "start", value: "biko" },
      { text: "操作", align: "center", value: "actions", sortable: false }
    ],
    datalists: [],
    editedIndex: -1,
    editedItem: {
      userid: "",
      macaddress: "",
      biko: ""
    },
    defaultItem: {
      userid: "",
      macaddress: "",
      biko: ""
    },
    valid: false, // バリデーション結果
    useridRules: [
      v => !!v || "必須です",
      v => /^[0-9]*$/.test(v) || "数値[0-9]で入力してください",
      v => v.length == 5 || "5文字で入力して下さい"
    ],
    macaddressRules: [
      v => !!v || "必須です",
      v =>
        /^[a-z0-9]*$/.test(v) || "数値[0-9] or 英小文字[a-z]で入力してください",
      v => v.length == 12 || "12文字で入力して下さい"
    ],
    bikoRules: [
      v => !!v || "必須です",
      v => v.length <= 20 || "20文字以内で入力して下さい"
    ],
    search: "", // フィルタリング検索キーワード
    snackbar: false,
    sbtext: "Completed!!",
    sbtimeout: 3000,
    alert: false, //alertを表示するか否かを制御
    alerttext: ""
  }),

  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "新規データ登録" : "データ修正";
    },
    isDisabled() {
      return this.editedIndex !== -1;
    },
    trimValue(value) {
      return value.replace(/^\s+|\s+$/g, "");
    }
  },

  watch: {
    dialog(val) {
      val || this.close();
    },
    dialogDelete(val) {
      val || this.closeDelete();
    }
  },

  created() {
    this.initialize();
  },

  methods: {
    initialize: async function() {
      this.datalists = [];
      try {
        let response = await axios.get("/v1/macaddress");
        this.datalists = response.data;
        //console.log("Index : record num=" + this.datalists.length);
      } catch (err) {
        this.alerttext = err;
        this.alert = true;
      }
    },

    editItem(item) {
      this.editedIndex = this.datalists.indexOf(item);
      //console.log("this.editedIndex1=" + this.editedIndex);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    deleteItem(item) {
      this.editedIndex = this.datalists.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },

    deleteItemConfirm: async function() {
      try {
        await axios.delete("/v1/macaddress/" + this.editedItem.userid);
        //console.log("Deleted : MACアドレス=" + this.editedItem.macaddress);
        this.datalists.splice(this.editedIndex, 1);
        this.snackbar = true;
      } catch (err) {
        this.alerttext = err;
        this.alert = true;
      }
      this.closeDelete();
    },

    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    createItem: async function(itemInfo) {
      try {
        await axios.post("/v1/macaddress", itemInfo);
        //console.log("Created : userid=" + response.data.userid);
        this.datalists.push(itemInfo);
        this.snackbar = true;
      } catch (err) {
        this.alerttext = err;
        this.alert = true;
      }
    },

    updateItem: async function(itemInfo, itemIndex) {
      try {
        await axios.post("/v1/macaddress", itemInfo);
        //console.log("Updated : userid=" + response.data.userid);
        Object.assign(this.datalists[itemIndex], itemInfo);
        this.snackbar = true;
      } catch (err) {
        this.alerttext = err;
        this.alert = true;
      }
    },

    save() {
      if (this.editedIndex > -1) {
        this.updateItem(this.editedItem, this.editedIndex);
      } else {
        this.createItem(this.editedItem);
      }
      this.close();
    }
  }
};
</script>
