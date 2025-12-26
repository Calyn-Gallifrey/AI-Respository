import React, { useEffect, useState } from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { View, Text, Image, TouchableOpacity, ScrollView, StyleSheet, Switch, Button, Alert } from 'react-native';
import axios from 'axios';

const Stack = createNativeStackNavigator();
const API_BASE = 'http://localhost:8080/api';

const settingOrder = [
  'tenure',
  'professional_title',
  'service_introduction',
  'honor_medals',
  'customer_comments',
  'total_customers',
  'total_claims',
  'policies_in_force',
  'total_coverage',
  'premium_services',
  'company_profile',
  'agent_highlights'
];

const settingLabels = {
  tenure: 'Show Tenure',
  professional_title: 'Professional Title',
  service_introduction: 'Service Introduction',
  honor_medals: 'Honor Medals',
  customer_comments: 'Customer Comments',
  total_customers: 'Total Customers (CNY)',
  total_claims: 'Total Claims (CNY)',
  policies_in_force: 'Policies In Force',
  total_coverage: 'Total Coverage (CNY)',
  premium_services: 'Premium Services',
  company_profile: 'Show Company Profile',
  agent_highlights: 'Agent Highlights'
};

function HomeScreen({ navigation }) {
  const [user, setUser] = useState(null);

  useEffect(() => {
    axios.get(`${API_BASE}/user/me`).then(res => setUser(res.data.data)).catch(console.error);
  }, []);

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation.navigate('Profile')}>
          <Image source={{ uri: user?.avatarUrl }} style={styles.avatar} />
        </TouchableOpacity>
        <View style={styles.statsRow}>
          <View style={styles.statBox}>
            <Text style={styles.statValue}>{user?.monthlyProduction ?? '--'}</Text>
            <Text style={styles.statLabel}>Monthly Production</Text>
          </View>
          <View style={styles.statBox}>
            <Text style={styles.statValue}>{user?.f2fRate ?? '--'}</Text>
            <Text style={styles.statLabel}>F2F Visit</Text>
          </View>
          <View style={styles.statBox}>
            <Text style={styles.statValue}>{user?.consecutiveEliteMonths ?? '--'}</Text>
            <Text style={styles.statLabel}>Consecutive Elite Months</Text>
          </View>
        </View>
      </View>
      <View style={styles.iconGrid}>
        {['AskBob','QR Scan','E Pocket','E Recruit','E Service'].map(label => (
          <View key={label} style={styles.iconItem}>
            <View style={styles.iconPlaceholder} />
            <Text style={styles.iconLabel}>{label}</Text>
          </View>
        ))}
      </View>
      <View style={styles.banner}><Text style={{color:'#fff'}}>News / Customer Manual / Leads</Text></View>
      <View style={styles.cardPlaceholder}><Text>Sales</Text></View>
      <View style={{height:40}} />
    </ScrollView>
  );
}

function ProfileScreen({ navigation }) {
  const [user, setUser] = useState(null);
  useEffect(() => { axios.get(`${API_BASE}/user/me`).then(res => setUser(res.data.data)); }, []);
  return (
    <ScrollView style={styles.container}>
      {user && (
        <View style={styles.profileHeader}>
          <Image source={{ uri: user.avatarUrl }} style={styles.largeAvatar} />
          <Text style={styles.profileName}>{user.name}</Text>
          <Text style={styles.profileSubtitle}>{user.role}</Text>
          <Text style={styles.profileSubtitle}>{user.branch}</Text>
        </View>
      )}
      <TouchableOpacity style={styles.listItem} onPress={() => navigation.navigate('Cards')}>
        <Text>My Business Card</Text>
      </TouchableOpacity>
      {['E Points','My Insurance Store','My Favorites','Feedback','New User Guide','Help','Complaint'].map(item => (
        <View key={item} style={styles.listItem}><Text>{item}</Text></View>
      ))}
    </ScrollView>
  );
}

function CardsScreen({ navigation }) {
  const [cards, setCards] = useState([]);

  const load = () => {
    axios.get(`${API_BASE}/cards`).then(res => setCards(res.data.data || [])).catch(console.error);
  };

  useEffect(() => {
    const unsubscribe = navigation.addListener('focus', load);
    return unsubscribe;
  }, [navigation]);

  return (
    <ScrollView style={styles.container}>
      <Text style={styles.sectionTitle}>Link Card</Text>
      <View style={styles.cardRow}>
        {cards.map(card => (
          <View key={card.id} style={styles.linkCard}>
            <Image source={{ uri: 'https://via.placeholder.com/150x80' }} style={styles.cardImage} />
            <Text style={styles.cardTitle}>{card.title}</Text>
            <View style={styles.cardActions}>
              <TouchableOpacity style={styles.shareBtn}><Text>Share</Text></TouchableOpacity>
              <TouchableOpacity style={styles.editBtn} onPress={() => navigation.navigate('CardSettings', { cardId: card.id, title: card.title })}>
                <Text>Edit</Text>
              </TouchableOpacity>
            </View>
          </View>
        ))}
      </View>
      <Text style={styles.sectionTitle}>Image Card</Text>
      <View style={styles.placeholderBox} />
      <Text style={styles.sectionTitle}>Custom Poster Business Card</Text>
      <View style={styles.placeholderBox} />
    </ScrollView>
  );
}

function CardSettingsScreen({ route, navigation }) {
  const { cardId, title } = route.params;
  const [settings, setSettings] = useState({});

  const loadSettings = () => {
    axios.get(`${API_BASE}/cards/${cardId}/settings`).then(res => setSettings(res.data.data || {}));
  };

  useEffect(() => {
    loadSettings();
  }, [cardId]);

  const toggle = (key) => {
    setSettings(prev => ({ ...prev, [key]: !prev[key] }));
  };

  const handleSave = async () => {
    const payload = { settings: settingOrder.map(key => ({ settingKey: key, isVisible: !!settings[key] })) };
    try {
      await axios.put(`${API_BASE}/cards/${cardId}/settings`, payload);
      navigation.navigate('Cards');
    } catch (e) {
      Alert.alert('Save failed', 'Please try again');
    }
  };

  return (
    <ScrollView style={styles.container}>
      <Text style={styles.settingsTitle}>{title} Link Card</Text>
      {settingOrder.map(key => (
        <View key={key} style={styles.settingRow}>
          <View style={{ flex: 1 }}>
            <Text style={styles.settingLabel}>{settingLabels[key]}</Text>
          </View>
          <View style={styles.switchWrapper}>
            <Text style={styles.hideLabel}>{settings[key] ? 'Show' : 'Hide'}</Text>
            <Switch value={!!settings[key]} onValueChange={() => toggle(key)} />
          </View>
        </View>
      ))}
      <TouchableOpacity style={styles.saveButton} onPress={handleSave}>
        <Text style={styles.saveText}>Save</Text>
      </TouchableOpacity>
    </ScrollView>
  );
}

export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Home" component={HomeScreen} options={{ headerShown: false }} />
        <Stack.Screen name="Profile" component={ProfileScreen} options={{ title: 'Profile' }} />
        <Stack.Screen name="Cards" component={CardsScreen} options={{ title: 'Business Card Management' }} />
        <Stack.Screen name="CardSettings" component={CardSettingsScreen} options={{ title: 'Link Card' }} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}

const styles = StyleSheet.create({
  container: { flex:1, backgroundColor:'#f6f6f6' },
  header: { padding:16, backgroundColor:'#fff' },
  avatar: { width:50, height:50, borderRadius:25, marginBottom:12 },
  statsRow: { flexDirection:'row', justifyContent:'space-between' },
  statBox: { alignItems:'center', flex:1 },
  statValue: { fontSize:18, fontWeight:'700' },
  statLabel: { fontSize:12, color:'#555', textAlign:'center' },
  iconGrid: { flexDirection:'row', flexWrap:'wrap', backgroundColor:'#fff', paddingVertical:16 },
  iconItem: { width:'20%', alignItems:'center', marginBottom:12 },
  iconPlaceholder: { width:40, height:40, backgroundColor:'#ddd', borderRadius:20, marginBottom:6 },
  iconLabel: { fontSize:12, textAlign:'center' },
  banner: { margin:16, padding:16, backgroundColor:'#ff8a00', borderRadius:10, alignItems:'center' },
  cardPlaceholder: { margin:16, padding:24, backgroundColor:'#fff', borderRadius:12 },
  profileHeader: { alignItems:'center', padding:20, backgroundColor:'#fff' },
  largeAvatar: { width:80, height:80, borderRadius:40, marginBottom:8 },
  profileName: { fontSize:18, fontWeight:'700' },
  profileSubtitle: { color:'#555' },
  listItem: { padding:16, backgroundColor:'#fff', borderBottomWidth:1, borderColor:'#eee' },
  sectionTitle: { marginTop:16, marginLeft:16, fontWeight:'700' },
  cardRow: { flexDirection:'row', flexWrap:'wrap', padding:16, gap:12 },
  linkCard: { width:'46%', backgroundColor:'#fff', borderRadius:12, padding:10, marginBottom:12 },
  cardImage: { width:'100%', height:80, borderRadius:8, marginBottom:8, backgroundColor:'#eee' },
  cardTitle: { fontWeight:'700', marginBottom:8 },
  cardActions: { flexDirection:'row', justifyContent:'space-between' },
  shareBtn: { padding:8, backgroundColor:'#f0f0f0', borderRadius:8 },
  editBtn: { padding:8, backgroundColor:'#ffd699', borderRadius:8 },
  placeholderBox: { height:100, backgroundColor:'#fff', margin:16, borderRadius:12 },
  settingsTitle: { padding:16, fontWeight:'700' },
  settingRow: { flexDirection:'row', alignItems:'center', padding:16, backgroundColor:'#fff', borderBottomWidth:1, borderColor:'#eee' },
  settingLabel: { fontSize:14 },
  switchWrapper: { flexDirection:'row', alignItems:'center' },
  hideLabel: { marginRight:8, color:'#c77d00' },
  saveButton: { margin:16, backgroundColor:'#ff9b00', padding:16, borderRadius:24, alignItems:'center' },
  saveText: { color:'#fff', fontWeight:'700' }
});
