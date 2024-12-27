import 'package:flutter/material.dart';
import 'package:flutter_locatrip/checklist/screen/add_item_screen.dart';

class ChecklistWidget extends StatelessWidget {
  final Map<String, dynamic> category;
  final Function(int, bool) onItemChecked;
  final VoidCallback onItemAdd;
  final bool isEditing;  // 편집 모드 여부
  final VoidCallback onDelete;  // 삭제 버튼 클릭 시 호출되는 콜백

  ChecklistWidget({
    required this.category,
    required this.onItemChecked,
    required this.onItemAdd,
    required this.isEditing,
    required this.onDelete,
  });

  @override
  Widget build(BuildContext context) {
    return ExpansionTile(
      title: Row(
        children: [
          Text(
            category['name'],
            style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
          ),
        ],
      ),
      trailing: isEditing
      ? IconButton(
        icon: Icon(Icons.delete),
        onPressed: onDelete,
      )
      : Icon(Icons.expand_more),
      children: [
        // 카테고리 내 아이템들
        ...List.generate(
          category['items'].length,
              (index) {
            var item = category['items'][index];
            return CheckboxListTile(
              title: Text(item['name']),
              value: item['isChecked'] ?? false,
              onChanged: (bool? value) {
                onItemChecked(index, value ?? false);
              },
            );
          },
        ).toList(),
        // 아이템 추가 버튼
        CheckboxListTile(
          title: Text('아이템 추가'),
          value: false,  // 기본적으로 체크되지 않음
          onChanged: (bool? value) {
            onItemAdd();  // 아이템 추가 콜백 호출
          },
        ),
      ],
    );
  }
}
