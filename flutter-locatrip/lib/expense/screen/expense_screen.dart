import 'package:flutter/material.dart';
import 'package:flutter_locatrip/expense/model/expense_model.dart';

class ExpenseScreen extends StatefulWidget {
  final int tripId;

  const ExpenseScreen({super.key, required this.tripId});

  @override
  State<ExpenseScreen> createState() => _ExpenseScreenState();
}

class _ExpenseScreenState extends State<ExpenseScreen> {
  final ExpenseModel expenseModel = ExpenseModel();
  Map<String, dynamic> groupedExpenses = {};
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    loadExpensesGroupedByDays();
  }

  Future<void> loadExpensesGroupedByDays() async {
    try {
      final data = await expenseModel.getExpensesGroupedByDays(widget.tripId);
      setState(() {
        groupedExpenses = data;
        isLoading = false;
      });
    } catch (e) {
      print('Error: $e');
      setState(() {
        isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('가계부')),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : ListView(
        children: [
          if (groupedExpenses.containsKey('preparation'))
            ExpansionTile(
              title: Text(
                '여행준비',
                style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
              ),
              children: (groupedExpenses['preparation']['expenses'] as List)
                  .map((expense) {
                return ListTile(
                  title: Text(expense['category']),
                  subtitle: Text(expense['description']),
                  trailing: Text('₩${expense['amount']}'),
                );
              }).toList(),
            ),

          ...groupedExpenses.entries
              .where((entry) => entry.key != 'preparation')
              .map((entry) {
            final day = entry.key;
            final dayData = entry.value as Map<String, dynamic>;
            final date = dayData['date'];

            return ExpansionTile(
              title: Text(
                '$day ($date)',
                style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
              ),
              children: (dayData['expenses'] as List).map((expense) {
                return ListTile(
                  title: Text(expense['category']),
                  subtitle: Text(expense['description']),
                  trailing: Text('₩${expense['amount']}'),
                );
              }).toList(),
            );
          }).toList(),
        ],
      ),
    );
  }
}
