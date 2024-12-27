import 'package:flutter/material.dart';
import 'package:flutter_locatrip/checklist/model/checklist_model.dart';
import 'package:flutter_locatrip/checklist/screen/add_category_screen.dart';
import 'package:flutter_locatrip/checklist/screen/add_item_screen.dart';
import 'package:flutter_locatrip/checklist/widget/checklist_widget.dart';

class ChecklistScreen extends StatefulWidget {
  const ChecklistScreen({super.key});

  @override
  State<ChecklistScreen> createState() => _ChecklistScreenState();
}

class _ChecklistScreenState extends State<ChecklistScreen> {
  final ChecklistModel _checklistModel = ChecklistModel();
  List<dynamic> _categories = [];
  int _selectedIndex = 0;
  bool _isEditing = false;

  @override
  void initState() {
    super.initState();
    _loadCategories();
  }

  void _loadCategories() async {
    try {
      var categories = await _checklistModel.getCategories();

      for (var category in categories) {
        var items = await _checklistModel.getItemsByCategory(category['id']);
        category['items'] = items;
      }
      setState(() {
        _categories = categories;
      });
    } catch (e) {
      print("카테고리 로드 중 오류 발생: $e");
    }
  }

  void _showDeleteConfirmation(int categoryId) {
    showModalBottomSheet(
      context: context,
      builder: (context) {
        return Container(
          padding: EdgeInsets.all(20),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Text('삭제', style: TextStyle(fontSize: 18)),
              SizedBox(height: 20),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  ElevatedButton(
                    onPressed: () {
                      Navigator.pop(context); // 하단 시트 닫기
                    },
                    child: Text('취소'),
                  ),
                  ElevatedButton(
                    onPressed: () async {
                      try {
                        await _checklistModel.deleteCategory(categoryId);
                        Navigator.pop(context); // 하단 시트 닫기
                        _loadCategories(); // 카테고리 재로딩
                      } catch (e) {
                        print("삭제 실패: $e");
                      }
                    },
                    child: Text('삭제'),
                  ),
                ],
              ),
            ],
          ),
        );
      },
    );
  }

  void _addCategory(BuildContext context) async {
    final result = await Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => AddCategoryScreen()),
    );

    if (result != null && result) {
      _loadCategories();
    }
  }

  void _updateItemCheckedStatus(int itemId, bool isChecked) async {
    try {
      await _checklistModel.updateItemCheckedStatus(itemId, isChecked);
    } catch (e) {
      print("체크 상태 업데이트 중 오류 발생: $e");
    }
  }

  void _navigateToAddItemScreen(BuildContext context, int categoryId) async {
    final result = await Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => AddItemScreen(categoryId: categoryId),
      ),
    );

    if (result != null) {
      _loadCategories();
    }
  }

  void _onTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("체크리스트"),
        actions: [
          TextButton(
            onPressed: () {
              setState(() {
                _isEditing = !_isEditing;
              });
            },
            child: Text(
              '편집',
              style: TextStyle(color: Colors.blue),
            ),
          ),
        ],
      ),
      body: _categories.isEmpty
          ? Center(child: CircularProgressIndicator())
          : ListView.builder(
        itemCount: _categories.length + 1,
        itemBuilder: (context, index) {
          if (index < _categories.length) {
            return Column(
              children: [
                ChecklistWidget(
                  category: _categories[index],
                  onItemChecked: (itemIndex, isChecked) {
                    _updateItemCheckedStatus(
                      _categories[index]['items'][itemIndex]['id'],
                      isChecked,
                    );
                    setState(() {
                      _categories[index]['items'][itemIndex]
                      ['isChecked'] = isChecked;
                    });
                  },
                  onItemAdd: () {
                    _navigateToAddItemScreen(
                        context, _categories[index]['id']);
                  },
                  isEditing: _isEditing,
                  onDelete: () {
                    _showDeleteConfirmation(_categories[index]['id']);
                  },
                ),
              ],
            );
          } else {
            return Padding(
              padding: const EdgeInsets.all(16.0),
              child: Center(
                child: SizedBox(
                  width: 150, // Set fixed width for the button
                  child: ElevatedButton(
                    onPressed: () => _addCategory(context),
                    child: Text('카테고리 추가',
                    style: TextStyle(color: Colors.white),
                    ),
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.blueAccent,
                      padding: EdgeInsets.symmetric(vertical: 15), // Adjust padding
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(15),
                      ),
                    ),
                  ),
                ),
              ),
            );
          }
        },
      ),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _selectedIndex,
        onTap: _onTapped,
        items: const [
          BottomNavigationBarItem(
              icon: Icon(Icons.home_outlined), label: "홈"),
          BottomNavigationBarItem(
              icon: Icon(Icons.map_outlined), label: "지도"),
          BottomNavigationBarItem(
              icon: Icon(Icons.recommend_outlined), label: "여행첨삭소"),
          BottomNavigationBarItem(
              icon: Icon(Icons.sms_outlined), label: "채팅"),
          BottomNavigationBarItem(
              icon: Icon(Icons.account_circle_outlined), label: "마이페이지"),
        ],
      ),
    );
  }
}
